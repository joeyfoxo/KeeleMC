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

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String uuidStr = in.readUTF();
            int permissionCount = in.readInt();

            List<String> permissions = new ArrayList<>();
            for (int i = 0; i < permissionCount; i++) {
                String perm = in.readUTF();
                permissions.add(perm);
            }

            UUID uuid = UUID.fromString(uuidStr);
            IronPermissionProvider.setPermissions(uuid, permissions);

        } catch (IOException e) {
            System.err.println("[KeeleBridge] Error while reading plugin message:");
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("[KeeleBridge] Unexpected error:");
            ex.printStackTrace();
        }
    }
}