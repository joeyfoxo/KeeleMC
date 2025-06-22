package dev.joeyfoxo.keelehub.Interactables.hubselector.item;

import dev.joey.keelecore.util.ItemTagHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static dev.joey.keelecore.util.UtilClass.createItem;

public class SelectorItem {

    ItemStack selector;

    public SelectorItem() {
        ItemStack selector = createItem(Material.COMPASS,
                Component.text()
                        .content("Hub Selector")
                        .color(TextColor.color(203, 114, 18))
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true).build(),
                Component.text()
                        .content("Use this selector to enter a new gamemode of your choice")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(100, 100, 100)).build());

        ItemTagHandler.setTag(selector, "inventory_item", PersistentDataType.STRING, "hubselector");

        this.selector = selector;
    }

    public ItemStack getSelector() {
        return selector;
    }

}
