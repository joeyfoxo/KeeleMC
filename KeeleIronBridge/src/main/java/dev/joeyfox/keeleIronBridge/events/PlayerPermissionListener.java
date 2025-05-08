package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.*;

public class PlayerPermissionListener {

    private final Map<UUID, List<String>> tempPermissions = new HashMap<>();
    private static final MinecraftChannelIdentifier CHANNEL = MinecraftChannelIdentifier.from("keele:playerinfo");

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) return;
        if (!(event.getSource() instanceof com.velocitypowered.api.proxy.ServerConnection)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        UUID uuid = UUID.fromString(in.readUTF());
        String rank = in.readUTF(); // optional rank name
        int count = in.readInt();
        List<String> perms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            perms.add(in.readUTF());
        }

        tempPermissions.put(uuid, perms);

        System.out.println("[Velocity] Received permissions for " + uuid + " (rank: " + rank + "): " + perms);
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        List<String> perms = tempPermissions.remove(uuid);

        if (perms == null) {
            System.out.println("[Velocity] No permissions received for " + player.getUsername() + ", using default.");
            return;
        }

        PermissionProvider provider = _ -> {
            return node -> perms.contains(node) ? Tristate.TRUE : Tristate.UNDEFINED;
        };

        System.out.println("[Velocity] Applying permissions to " + player.getUsername() + ": " + perms);
        event.setProvider(provider);
    }
}