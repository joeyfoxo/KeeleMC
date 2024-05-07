package dev.joey.keelecore.admin.listeners;

import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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

import static dev.joey.keelecore.admin.vanish.VanishCommand.getVanishedPlayers;
import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class BlockDefaultThings implements Listener {

    public BlockDefaultThings() {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        String command = event.getMessage();
        List<String> blockedCommands = new ArrayList<>(Arrays.asList(
                "plugin", "pl", "plugins",
                "?", "help", "about",
                "ver", "version", "bukkit",
                "fastasyncworldedit", "fawe", "worldedit",
                "icanhasbukkit", "litebans", "luckperms",
                "paper", "spigot", "spark",
                "velocity", "waterfall", "bungee"));
        if (!player.getUniqueId().equals(UUID.fromString("0d3df835-eed7-49a2-be2a-82be4d64bc27"))) {
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

        getVanishedPlayers().forEach(player -> event.getPlayer().hidePlayer(keeleCore, Bukkit.getPlayer(UUID.fromString(player))));
        if (getVanishedPlayers().contains(event.getPlayer().getUniqueId().toString())) {
            event.joinMessage(Component.text(""));
            return;
        }
        event.joinMessage(Component.text()
                .content("[").color(TextColor.color(UtilClass.gray))
                .append(Component.text("+")
                        .style(Style.style(TextColor.color(UtilClass.success), TextDecoration.BOLD)))
                .append(Component.text("] ").color(TextColor.color(UtilClass.gray)))
                .append(Component.text(event.getPlayer().getName()))
                .color(TextColor.color(UtilClass.gray)).build());


    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerQuitEvent event) {
        if (getVanishedPlayers().contains(event.getPlayer().getUniqueId().toString())) {
            event.quitMessage(Component.text(""));
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(keeleCore, event.getPlayer());
            }
            return;
        }
        event.quitMessage(Component.text()
                .content("[").color(TextColor.color(UtilClass.gray))
                .append(Component.text("-")
                        .style(Style.style(TextColor.color(UtilClass.error), TextDecoration.BOLD)))
                .append(Component.text("] ").color(TextColor.color(UtilClass.gray)))
                .append(Component.text(event.getPlayer().getName()))
                .color(TextColor.color(UtilClass.gray)).build());

    }

}
