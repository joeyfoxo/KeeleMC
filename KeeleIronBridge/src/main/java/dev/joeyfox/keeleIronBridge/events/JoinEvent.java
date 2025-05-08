package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.KeeleIronBridge;
import dev.joeyfox.keeleIronBridge.cache.PermissionCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JoinEvent {

    private final KeeleIronBridge plugin;
    private static final MinecraftChannelIdentifier CHANNEL = MinecraftChannelIdentifier.from("keele:rank_query");
    PermissionCache permissionCache = new PermissionCache();

    public JoinEvent(KeeleIronBridge plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        Optional<ServerConnection> serverConnection = player.getCurrentServer();

        if (serverConnection.isPresent()) {
            ServerConnection server = serverConnection.get();

            plugin.getLogger().info("Sending rank query for player: " + player.getUsername());

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("get_rank");
            out.writeUTF(player.getUniqueId().toString());

            server.sendPluginMessage(CHANNEL, out.toByteArray());
        } else {
            plugin.getLogger().warn("Player " + player.getUsername() + " is not connected to any server.");
        }
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) return;
        if (!(event.getSource() instanceof ServerConnection)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subchannel = in.readUTF();

        if (subchannel.equals("rank_response")) {
            UUID uuid = UUID.fromString(in.readUTF());
            String rank = in.readUTF();
            int count = in.readInt();
            var perms = new ArrayList<String>(count);
            for (int i = 0; i < count; i++) {
                perms.add(in.readUTF());
            }
            permissionCache.update(uuid, perms);

            plugin.getLogger().info("Received rank for " + uuid + ": " + rank);
            plugin.getLogger().info("Applying permissions for rank: " + rank);
            plugin.getLogger().info("Permissions: " + perms);

            // TODO: Implement permission assignment based on the received rank
        }

        event.setResult(PluginMessageEvent.ForwardResult.handled());
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        List<String> perms = permissionCache.getPermissions(player.getUniqueId());
        if (perms == null) return;

        System.out.println("Permissions for " + player.getUsername() + ": " + perms);

        // Build a provider that first checks our cached perms, then delegates to the default provider
        PermissionProvider defaultProvider = event.getProvider();
        PermissionProvider provider = subject -> {
            // Create the default PermissionFunction for this subject
            PermissionFunction defaultFunction = defaultProvider.createFunction(subject);
            return permission -> perms.contains(permission)
                    ? Tristate.TRUE
                    : defaultFunction.getPermissionValue(permission);
        };
        event.setProvider(provider);
    }

    private void performRankPermissions(String rank, List<String> perms) {
        switch (rank.toLowerCase()) {
            case "owner" -> {
                // Highest permission level
                System.out.println("Granting OWNER permissions.");
                // TODO: apply owner-level permissions
            }
            case "dev" -> {
                System.out.println("Granting DEV permissions.");
                // TODO: apply developer-level permissions
            }
            case "admin" -> {
                System.out.println("Granting ADMIN permissions.");
                // TODO: apply admin-level permissions
            }
            case "mod" -> {
                System.out.println("Granting MOD permissions.");
                // TODO: apply moderator-level permissions
            }
            case "helper" -> {
                System.out.println("Granting HELPER permissions.");
                // TODO: apply helper-level permissions
            }
            case "student" -> {
                System.out.println("Granting STUDENT permissions.");
                // TODO: apply student-level permissions
            }
            case "guest" -> {
                System.out.println("Granting GUEST permissions.");
                // TODO: apply guest-level permissions
            }
            case "player" -> {
                System.out.println("Granting PLAYER permissions.");
                // TODO: apply base player permissions
            }
            default -> {
                System.out.println("Unknown rank: " + rank);
                // TODO: apply default/guest fallback permissions
            }
        }
    }
}