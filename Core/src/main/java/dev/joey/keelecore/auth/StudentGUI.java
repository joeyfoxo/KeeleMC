package dev.joey.keelecore.auth;

import dev.joey.keelecore.util.PlayerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StudentGUI implements PlayerGUI {

    Inventory GUI;

    public ItemStack keeleStudent = createItem(Material.LIME_WOOL,
            Component.text("Student")
                    .style(Style.style(TextColor.color(36, 191, 41), TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                    .toBuilder().build());

    public ItemStack nonStudent = createItem(Material.BLUE_WOOL,
            Component.text("Non Student")
                    .style(Style.style(TextColor.color(61, 158, 191), TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                    .toBuilder().build());

    public StudentGUI(InventoryHolder owner, int size, Component title) {
        createGUI(owner, size, title);
    }

    @Override
    public void createGUI(InventoryHolder owner, int size, Component title) {

        GUI = Bukkit.createInventory(owner, size, title);
        GUI.setItem(3, keeleStudent);
        GUI.setItem(5, nonStudent);

    }

    @Override
    public void closeGUI(Player player) {
        player.closeInventory();
    }

    @Override
    public void openGUI(Player player) {

        player.openInventory(GUI);

    }

    public Inventory getGUI() {

        return GUI;

    }

    @Override
    public ItemStack createItem(Material material, Component component) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        meta.displayName(component);
        item.setItemMeta(meta);
        return item;

    }

    @Override
    public ItemStack createItem(Material material, Component component, int ItemID) {
        return null;
    }

    @Override
    public ItemStack createItem(Material material, Component component, int ItemID, List<Component> lore) {
        return null;
    }
}
