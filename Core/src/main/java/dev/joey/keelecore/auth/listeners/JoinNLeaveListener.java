package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.armour.galaxy.GalaxyArmour;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionManager.getRank(player);
        PermissionManager.getVanishValueFromDatabaseAsync(player, isVanished ->
                PermissionManager.setVanished(player, isVanished));


        PermissionManager.getPlayer(player.getUniqueId()).thenAccept(player1 -> {
            if (player1.getRank() == PlayerRank.DEV) {
                Set<ItemStack> armor = GalaxyArmour.createFullSet();
                for (ItemStack item : armor) {
                    player.getInventory().addItem(item);
                }
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        PermissionManager.getPlayer(uuid)
                .thenApply(player -> player != null ? player : new KeelePlayer(event.getPlayer()))
                .thenAccept(PermissionManager::put) // Save current state to DB and update cache if needed
                .thenRun(() -> PermissionManager.remove(uuid)); // Remove from cache
    }
}
