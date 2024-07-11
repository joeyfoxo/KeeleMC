package dev.joey.keelecore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface PlayerGUI {

    void createGUI(InventoryHolder owner, int size, Component title);

    void closeGUI(Player player);

    void openGUI(Player player);

    ItemStack createItem(Material material, Component component);

    ItemStack createItem(Material material, Component component, int ItemID);

    ItemStack createItem(Material material, Component component, int ItemID, List<Component> lore);

}
