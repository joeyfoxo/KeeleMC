package dev.joey.keelecore.util.GUI;

import dev.joey.keelecore.util.ItemTagHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack clicked = event.getItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String type = ItemTagHandler.getTag(clicked, "inventory_item", PersistentDataType.STRING);

        if (type == null) return;

        switch (type) {
            default -> event.getPlayer().sendMessage(ChatColor.RED + "Unknown cosmetic type.");
        }
    }

}
