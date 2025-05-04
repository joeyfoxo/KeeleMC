package dev.joey.keelecore.armour.galaxy;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.awt.*;

public class ColorCycleTask implements Runnable {
    private static int tick = 0;

    private static final NamespacedKey TICK_KEY = new NamespacedKey("keelecore", "local_tick");

    public static int getTick() {
        return tick;
    }

    @Override
    public void run() {
        tick++;
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (isColorCycleArmor(item)) {
                    applyColor(item, tick);
                }
            }
        }
    }

    public static boolean isColorCycleArmor(ItemStack item) {
        return GalaxyArmour.isColorCycleArmor(item);
    }

    public static void applyColor(ItemStack item, int globalTick) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        // Local sync target ~30 ticks behind global to avoid chasing a moving target
        int syncTarget = globalTick - 30;
        int localTick = container.getOrDefault(TICK_KEY, PersistentDataType.INTEGER, syncTarget - 20);

        // Catch up to syncTarget
        if (localTick < syncTarget) {
            localTick += 4; // speed up catch-up
            if (localTick > syncTarget) localTick = syncTarget;
        }

        float hue = (localTick % 360) / 360f;
        java.awt.Color awtColor = java.awt.Color.getHSBColor(hue, 1.0f, 1.0f);
        org.bukkit.Color bukkitColor = org.bukkit.Color.fromRGB(
                awtColor.getRed(),
                awtColor.getGreen(),
                awtColor.getBlue()
        );

        meta.setColor(bukkitColor);
        container.set(TICK_KEY, PersistentDataType.INTEGER, localTick);
        item.setItemMeta(meta);
    }
}