package dev.joeyfoxo.keelehub.Interactables;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.util.GUI.GUIRegistry;
import dev.joey.keelecore.util.ItemTagHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class ItemListener implements Listener {

    public ItemListener() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
        keeleHub.getServer().getMessenger().registerOutgoingPluginChannel(keeleHub, "BungeeCord");
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        String gamemodeItem = ItemTagHandler.getTag(clicked, "gamemode", PersistentDataType.STRING);
        if (gamemodeItem == null) {
            return;
        }

        // Cancel event only if the "hubselector" GUI is open
        if (GUIRegistry.getGUI("hubselector", player) != null) {
            event.setCancelled(true);

            switch (gamemodeItem) {
                case "survival", "modded", "test" -> sendConnectMessage(player, gamemodeItem);
                default -> {

                }
            }
        }
    }

    private void sendConnectMessage(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(serverName);
        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
    }

}
