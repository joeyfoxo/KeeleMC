package dev.joey.keelecore.util;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class UtilClass {

    static Logger log = Logger.getLogger("Minecraft");
    public static int success = new Color(0, 255, 8).getRGB();
    public static int error = new Color(202, 28, 26).getRGB();
    public static int information = new Color(255, 221, 0).getRGB();
    public static int gray = new Color(115, 115, 115).getRGB();

    public static boolean noAccessMessage(Object instance, KeelePlayer keelePlayer) {
        if (RankGuard.hasRequiredRank(instance, keelePlayer)) {
            return false;
        }

        RequireRank annotation = instance.getClass().getAnnotation(RequireRank.class);
        String required = (annotation != null) ? annotation.value().name() : "Unknown";

        Component message = Component.text("✖ You lack permission. Required Rank: ", TextColor.color(0xFF5555))
                .append(PlayerRank.fromString(required).getPrefix());

        UtilClass.sendPlayerMessage(keelePlayer, message);        return true;
    }

    //TODO CHANGE THIS
    public static void sendPlayerMessage(Player player, String message, int colour) {
        player.sendMessage(Component.text().content(message).color(TextColor.color(colour)));
    }

    public static void sendPlayerMessage(KeelePlayer player, String message, int colour) {
        player.getPlayer().sendMessage(Component.text().content(message).color(TextColor.color(colour)));
    }

    public static void sendPlayerMessage(Collection<? extends Player> players, Component component) {
        players.forEach(player -> player.sendMessage(component));
    }

    public static void sendPlayerMessage(KeelePlayer player, Component message) {
        player.getPlayer().sendMessage(message);
    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static Boolean percentageChance(double chance) {
        return Math.random() <= chance;
    }

    public static void sendTitleMessage(Player player, String text, String subtitle, int fadeIn, int stay, int fadeOut) {
        Title title = Title.title(
                Component.text(text),
                Component.text(subtitle),
                Title.Times.times(
                        Duration.ofSeconds(fadeIn),
                        Duration.ofSeconds(stay),
                        Duration.ofSeconds(fadeOut)));
        player.showTitle(title);
    }

    public static void sendTitleMessage(Collection<? extends Player> players, String text, String subtitle, int fadeIn, int stay, int fadeOut) {
        Title title = Title.title(
                Component.text(text),
                Component.text(subtitle),
                Title.Times.times(
                        Duration.ofSeconds(fadeIn),
                        Duration.ofSeconds(stay),
                        Duration.ofSeconds(fadeOut)));

        players.forEach(player -> player.showTitle(title));
    }

    public static TextColor colorFromRGB(int r, int g, int b) {
        return TextColor.color(r, g, b);
    }

    public static class TimesTickFormat {
        public static Map<String, Long> nameToTicks = new LinkedHashMap<>();

        static {

            nameToTicks.put("sunrise", 23000L);
            nameToTicks.put("dawn", 23000L);
            nameToTicks.put("daystart", 0L);
            nameToTicks.put("day", 0L);
            nameToTicks.put("morning", 1000L);
            nameToTicks.put("midday", 6000L);
            nameToTicks.put("noon", 6000L);
            nameToTicks.put("afternoon", 9000L);
            nameToTicks.put("sunset", 12000L);
            nameToTicks.put("dusk", 12000L);
            nameToTicks.put("sundown", 12000L);
            nameToTicks.put("nightfall", 12000L);
            nameToTicks.put("nightstart", 14000L);
            nameToTicks.put("night", 14000L);
            nameToTicks.put("midnight", 18000L);

        }

    }

    //TODO Look into this

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }

    public static final boolean isPaper;
    static {
        boolean paper;
        try {
            Class.forName("com.destroystokyo.paper.event.player.PlayerJumpEvent");
            paper = true;
        } catch (ClassNotFoundException e) {
            paper = false;
        }
        isPaper = paper;
    }

    public static ItemStack createItem(Material material, Component name, List<Component> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (UtilClass.isPaper) {
            meta.displayName(name);
            if (lore != null) {
                meta.lore(lore);
            }
        } else {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(name));
            if (lore != null) {
                List<String> legacyLore = lore.stream()
                        .map(c -> LegacyComponentSerializer.legacySection().serialize(c))
                        .toList();
                meta.setLore(legacyLore);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, TextColor color, Component... lores) {
        Component displayName = Component.text(name)
                .color(color)
                .decoration(TextDecoration.ITALIC, false);

        return createItem(material, displayName, lores);
    }

    public static ItemStack createItem(Material material, Component name, Component... lores) {
        List<Component> loreList = Arrays.asList(lores);
        return createItem(material, name, loreList);
    }
}
