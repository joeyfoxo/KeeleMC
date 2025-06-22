package dev.joeyfoxo.keelehub.Interactables;

import dev.joey.keelecore.util.GUI.GUIListener;
import dev.joey.keelecore.util.ItemTagHandler;
import dev.joeyfoxo.keelehub.Interactables.hubselector.HubSelector;
import dev.joeyfoxo.keelehub.KeeleHub;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemListener extends GUIListener implements Listener {

    public ItemListener() {
        KeeleHub.keeleHub.getServer().getPluginManager().registerEvents(this, KeeleHub.keeleHub);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        super.onPlayerInteract(event);

        ItemStack clicked = event.getItem();

        String type = ItemTagHandler.getTag(clicked, "inventory_item", PersistentDataType.STRING);

        switch (type) {
            case "hubselector" -> {
                HubSelector hubSelector = new HubSelector(ChatColor.GOLD, "Select Gamemode");
                hubSelector.open(event.getPlayer());
            }
            default -> event.getPlayer().sendMessage("§cUnknown item type.");
        }
    }

}
