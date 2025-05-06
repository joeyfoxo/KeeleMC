package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        System.out.println("TESTING NEW DEPLOYMENT ");
        PermissionManager.getPlayer(player.getUniqueId()).thenAccept(keelePlayer -> {

            keelePlayer.setPlayer(player);
            PermissionManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PermissionManager.getCached(uuid);
        PermissionManager.put(player).thenRun(() -> PermissionManager.remove(player.getUuid()));
    }
}
