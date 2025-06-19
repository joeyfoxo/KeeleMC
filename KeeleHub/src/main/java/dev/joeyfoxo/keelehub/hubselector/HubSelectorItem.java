package dev.joeyfoxo.keelehub.hubselector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HubSelectorItem {

    ItemStack selector = new ItemStack(Material.COMPASS);
    public HubSelectorItem() {
        createHubSelector(selector);
    }

    public ItemStack getHubSelector() {
        return selector;
    }

    private void createHubSelector(ItemStack selector) {

        ItemMeta meta = selector.getItemMeta();
        meta.displayName(Component.text()
                .content("Hub Selector")
                .color(TextColor.color(203, 114, 18))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build());
        meta.lore(List.of(Component.text()
                .content("Use this selector to enter a new gamemode of your choice")
                .decoration(TextDecoration.ITALIC, false)
                .color(TextColor.color(100, 100, 100)).build()));

        selector.setItemMeta(meta);

    }

}
