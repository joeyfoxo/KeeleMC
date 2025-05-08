package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionManager.getPlayer(player.getUniqueId()).thenAccept(keelePlayer -> {

            keelePlayer.setPlayer(player);
            PermissionManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(out);

            try {
                String rank = keelePlayer.getRank().name();
                List<String> permissions = keelePlayer.getRank().getPermissions();

                data.writeUTF(keelePlayer.getUuid().toString());   // UUID
                data.writeUTF(rank);                               // Rank
                data.writeInt(permissions.size());                 // Number of permissions

                for (String permission : permissions) {
                    data.writeUTF(permission);                     // Each permission string
                }

                player.sendPluginMessage(KeeleCore.getInstance(), "keele:rank", out.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PermissionManager.getCached(uuid);

        PermissionAttachment attachment = player.getPlayer().addAttachment(KeeleCore.getPlugin(KeeleCore.class));
        for (String permission : player.getRank().getPermissions()) {
            attachment.unsetPermission(permission);
        }
        player.getPlayer().removeAttachment(attachment);

        PermissionManager.put(player).thenRun(() -> PermissionManager.remove(player.getUuid()));
    }
}
