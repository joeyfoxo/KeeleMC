package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        KeelePlayer player = new KeelePlayer(event.getPlayer());
        if (player.getUuid().toString().equalsIgnoreCase("0d3df835-eed7-49a2-be2a-82be4d64bc27")) {
            System.out.println("Player is Owner");
            player.setRank(PlayerRank.OWNER);
        }
        PermissionManager.put(player);
    }

    @EventHandler
    public void onLeave(org.bukkit.event.player.PlayerQuitEvent event) {
        KeelePlayer player = PermissionManager.get(event.getPlayer().getUniqueId());
        PermissionManager.remove(player);
    }
}
