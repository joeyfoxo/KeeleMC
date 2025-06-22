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

    protected Inventory inventory;

    public GUI(ChatColor color, String title) {
        this.inventory = Bukkit.createInventory(null, 9, color + title);
    }

    public GUI(ChatColor color, String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, color + title);
    }

    public abstract String usageTag();

    public void open(Player player) {
        setupItems(inventory);
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    // To be implemented by subclasses to define their specific items
    protected abstract void setupItems(Inventory gui);

    public Inventory getInventory() {
        return inventory;
    }

}

