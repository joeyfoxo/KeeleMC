package dev.joeyfoxo.keelehub.managers;

import dev.joeyfoxo.keelehub.Interactables.hubselector.item.SelectorItem;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    private final SelectorItem HubSelectorItem = new SelectorItem();

    public ItemStack getHubSelectorItem() {
        return HubSelectorItem.getSelector();
    }

}
