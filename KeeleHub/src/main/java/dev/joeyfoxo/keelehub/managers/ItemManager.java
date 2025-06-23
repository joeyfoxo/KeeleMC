package dev.joeyfoxo.keelehub.managers;

import dev.joey.keelecore.util.GUI.GUIRegistry;
import dev.joeyfoxo.keelehub.Interactables.hubselector.HubSelector;
import dev.joeyfoxo.keelehub.Interactables.hubselector.item.SelectorItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    public ItemManager() {
        GUIRegistry.register("hubselector", player -> new HubSelector(ChatColor.GOLD, "Select Gamemode"));
    }

    private final SelectorItem HubSelectorItem = new SelectorItem();

    public ItemStack getHubSelectorItem() {
        return HubSelectorItem.getSelector();
    }

}
