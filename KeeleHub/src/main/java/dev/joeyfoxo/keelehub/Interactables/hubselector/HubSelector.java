package dev.joeyfoxo.keelehub.Interactables.hubselector;

import dev.joey.keelecore.util.GUI.GUI;
import dev.joey.keelecore.util.GUI.GUIRegistry;
import dev.joey.keelecore.util.ItemTagHandler;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class HubSelector extends GUI {

    public HubSelector(ChatColor color, String title) {
        super(color, title);
    }

    @Override
    public String usageTag() {
        return "hubselector";
    }

    @Override
    protected void setupItems(Inventory gui) {
        ItemStack survival = UtilClass.createItem(Material.GRASS_BLOCK,
                "Survival",
                TextColor.color(62, 237, 61),
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

        ItemStack modded = UtilClass.createItem(Material.BOOK,
                "Integrated Minecraft",
                TextColor.color(255, 194, 0), // Gold-like color
                Component.text()
                        .content("Version Forge 1.20.1")
                        .color(TextColor.color(84, 84, 84))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text().content("").build(),
                Component.text()
                        .content("Modded integrated Minecraft")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("is a modpack focused")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("on the best exploration experience.")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("Using quests, recipes, lore, loot,")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("and of course structures, this pack")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("feels like a cohesive game, not")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("just mods slapped together.")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("Explore to unlock powerful items")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build(),
                Component.text()
                        .content("through secret recipes and story.")
                        .color(TextColor.color(134, 134, 134))
                        .decoration(TextDecoration.ITALIC, false)
                        .build()
        );

        ItemStack testing = UtilClass.createItem(Material.REDSTONE,
                "Test Hub",
                TextColor.color(255, 65, 148), // Gold-like color
                Component.text()
                        .content("Version 1.20.1-1.21.6")
                        .color(TextColor.color(84, 84, 84))
                        .decoration(TextDecoration.ITALIC, false)
                        .build()
        );

        ItemTagHandler.setTag(survival, "gamemode", PersistentDataType.STRING, "survival");
        ItemTagHandler.setTag(survival, "gamemode", PersistentDataType.STRING, "modded");
        ItemTagHandler.setTag(survival, "gamemode", PersistentDataType.STRING, "test");

        gui.setItem(2, survival);
        gui.setItem(4, modded);
        gui.setItem(6, testing);
    }
}
