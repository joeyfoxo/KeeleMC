package dev.joey.keelecore.armour.galaxy;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

        int syncTarget = globalTick - 30;
        int localTick = container.getOrDefault(TICK_KEY, PersistentDataType.INTEGER, syncTarget - 20);

        // Instantly sync if more than 1 minute behind
        if (syncTarget - localTick > 1200) {
            localTick = syncTarget;
        } else if (localTick < syncTarget) {
            localTick += 4; // Smooth catch-up
            if (localTick > syncTarget) localTick = syncTarget;
        }

        // Apply your color logic here using localTick
        // e.g., meta.setColor(generateColorFromTick(localTick));

        container.set(TICK_KEY, PersistentDataType.INTEGER, localTick);
        item.setItemMeta(meta);

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