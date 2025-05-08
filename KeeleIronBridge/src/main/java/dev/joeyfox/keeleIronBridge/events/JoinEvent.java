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

import java.util.*;

public class JoinEvent {

    private final KeeleIronBridge plugin;
    public static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.from("keele:rank_query");

    // Store permissions temporarily until PermissionsSetupEvent is fired
    private final Map<UUID, List<String>> tempPermissions = new HashMap<>();

    public JoinEvent(KeeleIronBridge plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        plugin.getLogger().info("Sending rank query for " + player.getUsername() + " (" + uuid + ")");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("get_rank");
        out.writeUTF(uuid.toString());

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

        tempPermissions.put(uuid, perms); // store only for permission setup

        plugin.getLogger().info("Received rank for " + uuid + ": " + rank);
        plugin.getLogger().info("Permissions: " + perms);
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        List<String> perms = tempPermissions.remove(uuid); // remove after use

        if (perms == null) {
            plugin.getLogger().info("Permissions not yet received for " + player.getUsername());
            return;
        }

        plugin.getLogger().info("Applying permissions for " + player.getUsername() + ": " + perms);

        PermissionProvider provider = subject -> {
            return node -> perms.contains(node) ? Tristate.TRUE : Tristate.UNDEFINED;
        };

        event.setProvider(provider);
    }
}