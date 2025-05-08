package dev.joey.keelecore.armour.galaxy;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Color;

import java.util.HashSet;
import java.util.Set;

public class GalaxyArmour {

    private static final NamespacedKey COLOR_CYCLE_KEY = new NamespacedKey("keelecore", "color_cycle");

    public static boolean isColorCycleArmor(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        if (!(item.getItemMeta() instanceof LeatherArmorMeta)) return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(COLOR_CYCLE_KEY, PersistentDataType.BYTE);
    }

    public static Set<ItemStack> createFullSet() {
        Set<ItemStack> set = new HashSet<>();
        set.add(createPiece(Material.LEATHER_HELMET));
        set.add(createPiece(Material.LEATHER_CHESTPLATE));
        set.add(createPiece(Material.LEATHER_LEGGINGS));
        set.add(createPiece(Material.LEATHER_BOOTS));
        return set;
    }

    private static ItemStack createPiece(Material material) {
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        // Apply PersistentDataContainer tag
        meta.getPersistentDataContainer().set(COLOR_CYCLE_KEY, PersistentDataType.BYTE, (byte) 1);

        // Enchant glint
        meta.setEnchantmentGlintOverride(true);

        // Optional visual tweaks
        meta.displayName(Component.text("Galaxy Armor", NamedTextColor.AQUA));
        meta.setColor(Color.WHITE); // Initial color

        item.setItemMeta(meta);
        return item;
    }
}