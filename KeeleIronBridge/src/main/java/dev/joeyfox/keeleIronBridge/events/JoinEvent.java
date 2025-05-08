package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.KeeleIronBridge;
import dev.joeyfox.keeleIronBridge.cache.PermissionCache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JoinEvent {

    private final KeeleIronBridge plugin;
    private final PermissionCache permissionCache;
    public static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.from("keele:rank_query");

    // Tracks which players have had their permissions fully loaded
    private final Set<UUID> loadedPlayers = ConcurrentHashMap.newKeySet();

    public JoinEvent(KeeleIronBridge plugin) {
        this.plugin = plugin;
        this.permissionCache = new PermissionCache();
    }

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        plugin.getLogger().info("Sending rank query for: " + player.getUsername());

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("get_rank");
        out.writeUTF(player.getUniqueId().toString());

        event.getServer().sendPluginMessage(CHANNEL, out.toByteArray());
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!CHANNEL.equals(event.getIdentifier())) return;
        if (!(event.getSource() instanceof ServerConnection)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String sub = in.readUTF();
        if (!"rank_response".equals(sub)) return;

        UUID uuid = UUID.fromString(in.readUTF());
        String rank = in.readUTF();
        int count = in.readInt();
        List<String> perms = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            perms.add(in.readUTF());
        }

        permissionCache.update(uuid, perms);
        loadedPlayers.add(uuid);

        plugin.getLogger().info("Received rank for " + uuid + ": " + rank);
        plugin.getLogger().info("Applying permissions for rank: " + rank);
        plugin.getLogger().info("Perm list: " + perms);

        event.setResult(PluginMessageEvent.ForwardResult.handled());
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        List<String> perms = permissionCache.getPermissions(uuid);
        if (perms == null) {
            plugin.getLogger().warn("Permissions not loaded yet for " + player.getUsername() + ", denying by default.");
            perms = Collections.emptyList();
        }

        PermissionProvider defaultProv = event.getProvider();
        List<String> finalPerms = perms;
        PermissionProvider provider = subject -> {
            PermissionFunction fallback = defaultProv.createFunction(subject);
            return node -> {
                if (!loadedPlayers.contains(uuid)) {
                    plugin.getLogger().warn("Blocking permission '" + node + "' for " + player.getUsername() + " (permissions not ready)");
                    return Tristate.FALSE;
                }
                return finalPerms.contains(node) ? Tristate.TRUE : fallback.getPermissionValue(node);
            };
        };

        plugin.getLogger().info("Setting permission provider for " + player.getUsername());
        event.setProvider(provider);
    }
}