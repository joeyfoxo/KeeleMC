package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
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
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        PlayerPermManager.getPlayer(uuid).thenAccept(keelePlayer -> {
            keelePlayer.setPlayer(player);
            keelePlayer.setName(player.getName());
            PlayerPermManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());

            PermissionAttachment attachment = player.addAttachment(KeeleCore.getInstance());
            for (String permission : keelePlayer.getRank().getPermissions()) {
                attachment.setPermission(permission, true);
            }
            keelePlayer.setAttachment(attachment);

            //Test server port for Velocity compatibility
            Server server = Bukkit.getServer();
            PlayerRank rankToAccess = PlayerRank.MOD;
            if (server.getPort() == 25569) {
                if (!keelePlayer.getRank().hasPermissionLevel(rankToAccess)) {
                    server.getPlayer(keelePlayer.getUuid()).kick
                            (Component.text("✖ You lack permission. Required Rank: ", TextColor.color(0xFF5555))
                            .append(rankToAccess.getPrefix()));
                    return;
                }
            }


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

            } catch (IOException e) {
                System.out.println("[KeeleCore] Failed to send rank plugin message to Velocity for player: " + player.getName());
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PlayerPermManager.getCached(uuid);

        if (player != null && player.getAttachment() != null) {
            player.getPlayer().removeAttachment(player.getAttachment());

            PlayerPermManager.put(player).thenRun(() -> PlayerPermManager.remove(player.getUuid()));
        }
    }
}
