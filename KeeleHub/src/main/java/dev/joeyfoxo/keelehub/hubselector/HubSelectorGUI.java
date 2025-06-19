package dev.joeyfoxo.keelehub.hubselector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.util.PlayerGUI;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class HubSelectorGUI implements PlayerGUI, Listener {

    Inventory GUI;
    ByteArrayDataOutput output;

    ItemStack selector;

    public HubSelectorGUI(ItemStack selector) {
        createGUI(null, 9, Component.text("Select Gamemode").color(TextColor.color(168, 0, 82)));
        this.selector = selector;
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
        keeleHub.getServer().getMessenger().registerOutgoingPluginChannel(keeleHub, "BungeeCord");
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (UtilClass.isPaper) {

            if ((event.getAction().isRightClick()
                    || event.getAction().isLeftClick())
                    && (player.getInventory().getItemInMainHand().isSimilar(selector)
                    || player.getInventory().getItemInOffHand().isSimilar(selector))) {

                openGUI(player);
            }
        } else {
            if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK
                    || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                    && isSelectorItem(player.getInventory().getItemInMainHand())
                    || isSelectorItem(player.getInventory().getItemInOffHand())) {

                openGUI(player);
            }
        }
    }

    private boolean isSelectorItem(ItemStack item) {
        return item != null && item.getType() != Material.AIR && selector != null && item.isSimilar(selector);
    }

    private void createGUIItem(Material material, String title, TextColor color, int itemID, int ID, Component... lores) {

        GUI.setItem(ID, createItem(material, Component.text()
                .content(title)
                .color(color)
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), itemID, new ArrayList<>(Arrays.asList(lores))));
    }

    public void addItemsToGUI() {

        createGUIItem(Material.GRASS_BLOCK,
                "Survival",
                TextColor.color(62, 237, 61),
                0,
                2,
                Component.text()
                        .content("Version 1.16-1.20.4")
                        .color(TextColor.color(84, 84, 84))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text().content("").build(),
                Component.text()
                        .content("A special twist on survival with")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("custom terrain, unclaimed bases")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("can be raided, bounties and")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("so much more!")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build()
        );

        createGUIItem(Material.BARRIER,
                "Modded (Forge)",
                TextColor.color(163, 73, 164),
                1,
                4,
                Component.text()
                        .content("Requires Forge Client")
                        .color(TextColor.color(112, 6, 0))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text().content("").build(),
                Component.text()
                        .content("Join our Forge modpack server")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("You must install our mods.")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build()
        );


        createGUIItem(Material.TNT,
                "Uni Wars",
                TextColor.color(255, 81, 79),
                2,
                6,
                Component.text()
                        .content("Version 1.16-1.20.4")
                        .color(TextColor.color(84, 84, 84))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text().content("").build(),
                Component.text()
                        .content("Compete against 3 other teams")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("seperated by walls and gather")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("resources before the walls drop")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("and the fight begins!")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build());

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE) {
            if (event.getInventory().getType() == InventoryType.CREATIVE || event.getClickedInventory() == player.getInventory()) {
                event.setCancelled(false);
            }
        }

        if (event.getClickedInventory() == GUI) {

            if (event.getCurrentItem() != null) {

                switch (event.getCurrentItem().getItemMeta().getCustomModelData()) {

                    case 0 -> {

                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("survival");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }
                    case 1 -> {
                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("modded");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }
                    case 2 -> {
                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("uniwars");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }

                }
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void createGUI(InventoryHolder owner, int size, Component title) {
        if (UtilClass.isPaper) {
            GUI = Bukkit.createInventory(owner, size, title);
        } else {
            String legacyTitle = LegacyComponentSerializer.legacySection().serialize(title);
            GUI = Bukkit.createInventory(owner, size, legacyTitle);
        }
        addItemsToGUI();
    }

    @Override
    public void closeGUI(Player player) {

        player.closeInventory();

    }

    @Override
    public void openGUI(Player player) {
        player.openInventory(GUI);
    }

    @Override
    public ItemStack createItem(Material material, Component component) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (UtilClass.isPaper) {
            meta.displayName(component);
        } else {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(component));
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack createItem(Material material, Component component, int itemID) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(itemID);
        if (UtilClass.isPaper) {
            meta.displayName(component);
        } else {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(component));
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack createItem(Material material, Component component, int itemID, List<Component> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(itemID);

        if (UtilClass.isPaper) {
            meta.displayName(component);
            meta.lore(lore);
        } else {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(component));
            List<String> legacyLore = lore.stream()
                    .map(c -> LegacyComponentSerializer.legacySection().serialize(c))
                    .toList();
            meta.setLore(legacyLore);
        }

        item.setItemMeta(meta);
        return item;
    }
}
