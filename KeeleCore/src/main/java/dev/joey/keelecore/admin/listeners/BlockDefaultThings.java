package dev.joey.keelecore.admin.listeners;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dev.joey.keelecore.util.UtilClass.isPaper;
import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class BlockDefaultThings implements Listener {

    public BlockDefaultThings() {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        String command = event.getMessage();
        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        List<String> blockedCommands = new ArrayList<>(Arrays.asList(
                "plugin", "pl", "plugins",
                "?", "help", "about",
                "ver", "version", "bukkit",
                "fastasyncworldedit", "fawe", "worldedit",
                "icanhasbukkit", "litebans", "luckperms",
                "paper", "spigot", "spark",
                "velocity", "waterfall", "bungee"));
        if (!keelePlayer.getRank().hasPermissionLevel(PlayerRank.DEV)) {
            for (String blockedCommand : blockedCommands) {
                if (command.contains(blockedCommand)) {
                    event.setCancelled(true);
                    UtilClass.sendPlayerMessage(player, "This server runs custom plugins and versions, to report any bugs or issues contact developers or owners", UtilClass.information);
                    return;
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        if (isPaper) {
            Component message = Component.text("[", TextColor.color(UtilClass.gray))
                    .append(Component.text("+", TextColor.color(UtilClass.success), TextDecoration.BOLD))
                    .append(Component.text("] ", TextColor.color(UtilClass.gray)))
                    .append(Component.text(event.getPlayer().getName(), TextColor.color(UtilClass.gray)));

            event.joinMessage(message);
        } else {
            String legacy = "&7[&a&l+&7] &7" + event.getPlayer().getName();
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', legacy));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (isPaper) {
            Component message = Component.text("[", TextColor.color(UtilClass.gray))
                    .append(Component.text("-", TextColor.color(UtilClass.error), TextDecoration.BOLD))
                    .append(Component.text("] ", TextColor.color(UtilClass.gray)))
                    .append(Component.text(player.getName(), TextColor.color(UtilClass.gray)));

            event.quitMessage(message);
        } else {
            String legacy = "&7[&c&l-&7] &7" + player.getName();
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', legacy));
        }
    }
}
