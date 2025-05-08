package dev.joeyfox.keeleIronBridge.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.Player;
import dev.joeyfox.keeleIronBridge.events.IronPermissionProvider;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class PlayerPermissionListener {

    private final ProxyServer proxy;

    public PlayerPermissionListener(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        String channel = event.getIdentifier().getId();
        if (!channel.equals("keele:rank")) return;

        System.out.println("[KeeleBridge] Plugin message received on channel: " + channel);

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String uuidStr = in.readUTF();
            String rank = in.readUTF();
            int permissionCount = in.readInt();

            System.out.println("[KeeleBridge] Parsing permission data for UUID: " + uuidStr);
            System.out.println("[KeeleBridge] Rank: " + rank);
            System.out.println("[KeeleBridge] Permission count: " + permissionCount);

            List<String> permissions = new ArrayList<>();
            for (int i = 0; i < permissionCount; i++) {
                String perm = in.readUTF();
                permissions.add(perm);
                System.out.println("[KeeleBridge] Parsed permission: " + perm);
            }

            UUID uuid = UUID.fromString(uuidStr);
            IronPermissionProvider.setPermissions(uuid, permissions);

            System.out.println("[KeeleBridge] Permissions successfully applied to: " + uuid);

            Optional<Player> maybePlayer = proxy.getPlayer(uuid);
            if (maybePlayer.isPresent()) {
                System.out.println("[KeeleBridge] Player is online: " + maybePlayer.get().getUsername());
            } else {
                System.out.println("[KeeleBridge] Player is not currently connected to the proxy.");
            }

        } catch (IOException e) {
            System.err.println("[KeeleBridge] Error while reading plugin message:");
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("[KeeleBridge] Unexpected error:");
            ex.printStackTrace();
        }
    }
}