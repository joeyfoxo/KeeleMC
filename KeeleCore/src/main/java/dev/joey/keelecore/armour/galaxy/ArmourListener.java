package dev.joey.keelecore.armour.galaxy;

import dev.joey.keelecore.KeeleCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ArmourListener implements Listener {

    public ArmourListener() {
        // Register the event listener
        Bukkit.getPluginManager().registerEvents(this, KeeleCore.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Bukkit.getScheduler().runTaskLater(KeeleCore.getInstance(), () -> {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (ColorCycleTask.isColorCycleArmor(item)) {
                    ColorCycleTask.applyColor(item, ColorCycleTask.getTick());
                }
            }
        }, 1L); // Wait a tick for inventory update
    }

}
