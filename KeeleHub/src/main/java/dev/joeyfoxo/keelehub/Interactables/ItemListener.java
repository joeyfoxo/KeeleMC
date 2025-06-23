package dev.joeyfoxo.keelehub.Interactables;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.util.GUI.GUI;
import dev.joey.keelecore.util.GUI.GUIListener;
import dev.joey.keelecore.util.GUI.GUIRegistry;
import dev.joey.keelecore.util.ItemTagHandler;
import dev.joeyfoxo.keelehub.Interactables.hubselector.HubSelector;
import dev.joeyfoxo.keelehub.KeeleHub;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class ItemListener extends GUIListener implements Listener {

    public ItemListener() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
        keeleHub.getServer().getMessenger().registerOutgoingPluginChannel(keeleHub, "BungeeCord");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

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


    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) {
            System.out.println("[DEBUG] Clicked item is null or air.");
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        String inventoryItem = ItemTagHandler.getTag(clicked, "inventory_item", PersistentDataType.STRING);
        String gamemodeItem = ItemTagHandler.getTag(clicked, "gamemode", PersistentDataType.STRING);

        System.out.println("[DEBUG] Clicked by: " + player.getName());
        System.out.println("[DEBUG] inventory_item tag: " + inventoryItem);
        System.out.println("[DEBUG] gamemode tag: " + gamemodeItem);


        GUI gui = GUIRegistry.getGUI(inventoryItem);
        System.out.println("[DEBUG] GUI retrieved: " + gui);


        if (gui == null) {
            System.out.println("[DEBUG] GUI returned null for inventoryItem: " + inventoryItem);
            return;
        }

        System.out.println("[DEBUG] gui.getInventory(): " + gui.getInventory());
        System.out.println("[DEBUG] event.getInventory(): " + inventory);
        System.out.println("[DEBUG] gui.getInventory().equals(event.getInventory()): " + gui.getInventory().equals(inventory));

        if (gui.getInventory().equals(inventory)) {
            System.out.println("[DEBUG] Cancelling event because inventories match.");
            event.setCancelled(true);
        } else {
            System.out.println("[DEBUG] Inventories do not match; event not cancelled.");
        }

        ByteArrayDataOutput output;

        switch (gamemodeItem) {
            case "survival" -> {
                    output = ByteStreams.newDataOutput();
                    output.writeUTF("Connect");
                    output.writeUTF("survival");
                    player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                }
            default -> {
                player.sendMessage("§cUnknown gamemode item.");
            }
        }

    }

}
