package dev.joeyfoxo.keelehub.Interactables.hubselector;

import dev.joey.keelecore.util.GUI.GUI;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HubSelector extends GUI {


    public HubSelector(ChatColor color, String title) {
        super(color, title);
    }

    @Override
    public String usageTag() {
        return "hubselector";
    }

    @Override
    protected void setupItems(GUI gui) {
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

        gui.addItem(survival, 0);
    }
}
