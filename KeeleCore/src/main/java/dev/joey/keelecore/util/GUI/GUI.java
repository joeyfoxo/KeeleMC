package dev.joey.keelecore.util.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class GUI {

    protected Inventory inventory;

    public GUI(ChatColor color, String title) {
        this.inventory = Bukkit.createInventory(null, 9, color + title);
    }

    public GUI(ChatColor color, String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, color + title);
    }

    public abstract String usageTag();

    public void open(Player player) {
        setupItems(inventory, player);
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    // To be implemented by subclasses to define their specific items
    protected abstract void setupItems(Inventory gui, Player player);

    public Inventory getInventory() {
        return inventory;
    }

}

