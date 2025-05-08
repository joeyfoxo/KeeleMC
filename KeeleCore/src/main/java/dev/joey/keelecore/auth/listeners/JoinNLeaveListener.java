package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        PermissionManager.getPlayer(uuid).thenAccept(keelePlayer -> {
            keelePlayer.setPlayer(player);
            PermissionManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());

            // Prepare plugin message
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(out);

            try {
                String rank = keelePlayer.getRank().name();
                List<String> permissions = keelePlayer.getRank().getPermissions();

                data.writeUTF(keelePlayer.getUuid().toString());   // UUID
                data.writeUTF(rank);                               // Rank name
                data.writeInt(permissions.size());                 // Permission count

                for (String permission : permissions) {
                    data.writeUTF(permission);
                }

                player.sendPluginMessage(KeeleCore.getInstance(), "keele:rank", out.toByteArray());

                System.out.println("[KeeleCore] Sent rank plugin message to Velocity:");
                System.out.println(" - Player: " + player.getName() + " (" + uuid + ")");
                System.out.println(" - Rank: " + rank);
                System.out.println(" - Permissions (" + permissions.size() + "):");
                for (String perm : permissions) {
                    System.out.println("   • " + perm);
                }

            } catch (IOException e) {
                System.out.println("[KeeleCore] Failed to send rank plugin message to Velocity for player: " + player.getName());
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PermissionManager.getCached(uuid);

        PermissionManager.put(player).thenRun(() -> PermissionManager.remove(player.getUuid()));
    }
}
