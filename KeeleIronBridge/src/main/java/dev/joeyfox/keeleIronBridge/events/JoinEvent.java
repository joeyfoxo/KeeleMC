package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
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
    private final PermissionCache permissionCache;
    public static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.from("keele:rank_query");

    public JoinEvent(KeeleIronBridge plugin) {
        this.plugin = plugin;
        this.permissionCache = new PermissionCache();
    }

    /**
     * Query the backend on login so permissions are ready before setup.
     */
    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        plugin.getLogger().info("Sending rank query for: " + player.getUsername());

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("get_rank");
        out.writeUTF(player.getUniqueId().toString());

        // send to the server they joined
        Optional<ServerConnection> conn = player.getCurrentServer();
        conn.ifPresent(server -> server.sendPluginMessage(CHANNEL, out.toByteArray()));
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!CHANNEL.equals(event.getIdentifier())) return;
        if (!(event.getSource() instanceof ServerConnection)) return;

        var in = ByteStreams.newDataInput(event.getData());
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

        plugin.getLogger().info("Received rank for " + uuid + ": " + rank);
        plugin.getLogger().info("Applying permissions for rank: " + rank);
        plugin.getLogger().info("Perm list: " + perms);

        // apply any side-effects (prefixes, etc.)
        performRankPermissions(rank, perms);

        event.setResult(PluginMessageEvent.ForwardResult.handled());
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        List<String> perms = permissionCache.getPermissions(player.getUniqueId());
        if (perms == null) return;

        plugin.getLogger().info("Setting provider for " + player.getUsername() + " with perms: " + perms);

        PermissionProvider defaultProv = event.getProvider();
        PermissionProvider provider = subject -> {
            PermissionFunction df = defaultProv.createFunction(subject);
            return node -> perms.contains(node) ? Tristate.TRUE : df.getPermissionValue(node);
        };
        event.setProvider(provider);
    }

    private void performRankPermissions(String rank, List<String> perms) {
        // Example: set tab prefix, chat color, etc., based on rank
        switch (rank.toLowerCase()) {
            case "owner" -> plugin.getLogger().info("Granting OWNER features");
            case "dev"   -> plugin.getLogger().info("Granting DEV features");
            case "admin" -> plugin.getLogger().info("Granting ADMIN features");
            // add more as needed
            default       -> plugin.getLogger().info("No extra features for: " + rank);
        }
    }
}
