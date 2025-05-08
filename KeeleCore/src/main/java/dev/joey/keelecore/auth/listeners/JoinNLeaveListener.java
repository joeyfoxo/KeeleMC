package dev.joey.keelecore.auth.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionManager.getPlayer(player.getUniqueId()).thenAccept(keelePlayer -> {

            keelePlayer.setPlayer(player);
            PermissionManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(player.getUniqueId().toString());
            out.writeUTF(keelePlayer.getRank().name()); // include rank
            List<String> permissions = keelePlayer.getRank().getPermissions();
            out.writeInt(permissions.size());
            for (String perm : permissions) {
                out.writeUTF(perm);
            }

            Bukkit.getScheduler().runTask(KeeleCore.getInstance(), () -> {
                player.sendPluginMessage(KeeleCore.getInstance(), "keele:playerinfo", out.toByteArray());
                KeeleCore.getInstance().getLogger().info("Sent plugin message for " + player.getName());
            });
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PermissionManager.getCached(uuid);
        PermissionManager.put(player).thenRun(() -> PermissionManager.remove(player.getUuid()));
    }
}
