package dev.joey.keelecore.util;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UtilClass {


    public static KeeleCore keeleCore;

    static Logger log = Logger.getLogger("Minecraft");
    public static int success = new Color(0, 255, 8).getRGB();
    public static int error = new Color(202, 28, 26).getRGB();
    public static int information = new Color(255, 221, 0).getRGB();
    public static int gray = new Color(115, 115, 115).getRGB();

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
}
