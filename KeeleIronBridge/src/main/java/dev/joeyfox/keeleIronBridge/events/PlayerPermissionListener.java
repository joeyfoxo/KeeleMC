package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.KeeleIronBridge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPermissionListener {

    private final KeeleIronBridge plugin;

    private static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.from("keele:playerinfo");

    private final Map<UUID, List<String>> tempPermissions = new ConcurrentHashMap<>();
    private final Set<UUID> loadedPlayers = ConcurrentHashMap.newKeySet();
    private final Map<UUID, PermissionsSetupEvent> pendingEvents = new ConcurrentHashMap<>();

    public PlayerPermissionListener(KeeleIronBridge plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) return;
        if (!(event.getSource() instanceof com.velocitypowered.api.proxy.ServerConnection)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        UUID uuid = UUID.fromString(in.readUTF());
        String rank = in.readUTF(); // optional
        int count = in.readInt();
        List<String> perms = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            perms.add(in.readUTF());
        }

        tempPermissions.put(uuid, perms);
        loadedPlayers.add(uuid);

        System.out.println("[Velocity] Received permissions for " + uuid + " (rank: " + rank + "): " + perms);

        // If a setup event was waiting for this data, apply the permissions now
        PermissionsSetupEvent pending = pendingEvents.remove(uuid);
        if (pending != null && pending.getSubject() instanceof Player player) {
            applyProvider(pending, perms, player.getUsername());
        }
    }

    @Subscribe
    public void onPermissionSetup(PermissionsSetupEvent event) {
        if (!(event.getSubject() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        List<String> perms = tempPermissions.get(uuid);

        if (perms == null || !loadedPlayers.contains(uuid)) {
            System.out.println("[Velocity] Permissions not yet received for " + player.getUsername() + ", deferring setup.");
            pendingEvents.put(uuid, event);
            return;
        }

        applyProvider(event, perms, player.getUsername());
    }

    private void applyProvider(PermissionsSetupEvent event, List<String> perms, String username) {
        PermissionProvider provider = subject -> node -> {
            Tristate result = perms.contains(node) ? Tristate.TRUE : Tristate.UNDEFINED;
            System.out.println("[Velocity] Permission check: " + node + " => " + result);
            return result;
        };

        event.setProvider(provider);
        System.out.println("[Velocity] Permissions applied for " + username + ": " + perms);
    }
}