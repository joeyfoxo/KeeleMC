package dev.joeyfox.keeleIronBridge.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ProxyServer;

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
        if (!event.getIdentifier().getId().equals("keele:rank")) return;

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String uuidStr = in.readUTF();
            String rank = in.readUTF();
            int permissionCount = in.readInt();

            List<String> permissions = new ArrayList<>();
            for (int i = 0; i < permissionCount; i++) {
                permissions.add(in.readUTF());
            }

            UUID uuid = UUID.fromString(uuidStr);
            IronPermissionProvider.setPermissions(uuid, permissions);

            System.out.println("Received rank for " + uuid + ": " + rank + " with permissions:");
            permissions.forEach(p -> System.out.println("  - " + p));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}