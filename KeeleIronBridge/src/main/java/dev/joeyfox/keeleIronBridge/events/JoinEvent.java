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
        UUID uuid = player.getUniqueId();
        plugin.getLogger().info("[JoinEvent] Player connected: " + player.getUsername() + " (" + uuid + ")");
        plugin.getLogger().info("[JoinEvent] Sending rank query to backend...");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("get_rank");
        out.writeUTF(uuid.toString());

        event.getServer().sendPluginMessage(CHANNEL, out.toByteArray());
        plugin.getLogger().info("[JoinEvent] Plugin message sent to server for UUID: " + uuid);
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

        plugin.getLogger().info("[JoinEvent] Plugin message received for UUID: " + uuid);
        plugin.getLogger().info("[JoinEvent] Rank: " + rank + ", Permissions count: " + count);

        List<String> perms = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String perm = in.readUTF();
            perms.add(perm);
            plugin.getLogger().info("[JoinEvent] -> Perm: " + perm);
        }

        permissionCache.update(uuid, perms);
        loadedPlayers.add(uuid);

        plugin.getLogger().info("[JoinEvent] Cached permissions for UUID: " + uuid);
        plugin.getLogger().info("[JoinEvent] Permission cache now has: " + permissionCache.getPermissions(uuid));
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        plugin.getLogger().info("[JoinEvent] Permission setup for: " + player.getUsername() + " (" + uuid + ")");

        List<String> perms = permissionCache.getPermissions(uuid);
        boolean isLoaded = loadedPlayers.contains(uuid);
        plugin.getLogger().info("[JoinEvent] Permissions loaded? " + isLoaded + ", Found: " + perms);

        if (perms == null) {
            plugin.getLogger().warn("[JoinEvent] Permissions not loaded yet for " + player.getUsername() + ", using empty list.");
            perms = Collections.emptyList();
        }

        PermissionProvider defaultProv = event.getProvider();
        List<String> finalPerms = perms;
        PermissionProvider provider = subject -> {
            PermissionFunction fallback = defaultProv.createFunction(subject);
            return node -> {
                boolean loaded = loadedPlayers.contains(uuid);
                plugin.getLogger().info("[JoinEvent] Checking perm '" + node + "' for " + player.getUsername() + ", loaded: " + loaded);

                if (!loaded) {
                    plugin.getLogger().warn("[JoinEvent] -> Fallback value used for: " + node);
                    return fallback.getPermissionValue(node); // safer than denying
                }

                boolean has = finalPerms.contains(node);
                plugin.getLogger().info("[JoinEvent] -> Perm " + node + ": " + (has ? "GRANTED" : "FALLBACK"));
                return has ? Tristate.TRUE : fallback.getPermissionValue(node);
            };
        };

        plugin.getLogger().info("[JoinEvent] Applying PermissionProvider to " + player.getUsername());
        event.setProvider(provider);
    }
}