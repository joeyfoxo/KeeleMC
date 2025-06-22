package dev.joey.keelecore.util.GUI;

import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUI {

    protected GUI gui;
    protected Inventory inventory;

    public GUI(ChatColor color, String title) {
        this.inventory = Bukkit.createInventory(null, 9, color + title);
    }

    public GUI(ChatColor color, String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, color + title);
    }

    public abstract String usageTag();

    public void open(Player player) {
        setupItems(gui);
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public void addItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
    }

    // To be implemented by subclasses to define their specific items
    protected abstract void setupItems(GUI gui);

    public Inventory getInventory() {
        return inventory;
    }

    public GUI getGUI() {
        return gui;
    }
}

