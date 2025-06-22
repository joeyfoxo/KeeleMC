package dev.joey.keelecore.util;

import dev.joey.keelecore.KeeleCore;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemTagHandler {

    public static <T, Z> void setTag(ItemStack item, String keyName, PersistentDataType<T, Z> type, Z value) {
        if (item == null || keyName == null || type == null || value == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        NamespacedKey key = new NamespacedKey(KeeleCore.getInstance(), keyName);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, type, value);
        item.setItemMeta(meta);
    }

    public static <T, Z> Z getTag(ItemStack item, String keyName, PersistentDataType<T, Z> type) {
        if (item == null || keyName == null || type == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(KeeleCore.getInstance(), keyName);
        return meta.getPersistentDataContainer().get(key, type);
    }

    public static <T, Z> boolean hasTag(ItemStack item, String keyName, PersistentDataType<T, Z> type) {
        if (item == null || keyName == null || type == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(KeeleCore.getInstance(), keyName);
        return meta.getPersistentDataContainer().has(key, type);
    }

    public static <T, Z> void removeTag(ItemStack item, String keyName, PersistentDataType<T, Z> type) {
        if (item == null || keyName == null || type == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(KeeleCore.getInstance(), keyName);
        meta.getPersistentDataContainer().remove(key);
        item.setItemMeta(meta);
    }
}