package dev.joey.keelecore.server.restarting;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class DailyRestart {

    public DailyRestart() {
        // Schedule a task to announce the restart 5 minutes before it happens
        Bukkit.getScheduler().runTaskLater(keeleCore, () -> {
            Bukkit.broadcast(Component.text("Server will restart in 5 minutes.", NamedTextColor.RED));
        }, 1728000 - 6000); // 6000 ticks = 5 minutes

        // Schedule a task to announce the restart 1 minute before it happens
        Bukkit.getScheduler().runTaskLater(keeleCore, () -> {
            Bukkit.broadcast(Component.text("Server will restart in 1 minute.", NamedTextColor.RED));
        }, 1728000 - 1200); // 1200 ticks = 1 minute

        // Schedule the restart task
        Bukkit.getScheduler().runTaskLater(keeleCore, () -> {
            keeleCore.getServer().getWorlds().forEach(World::save);
            keeleCore.getServer().savePlayers();
            keeleCore.getServer().spigot().restart();
        }, 1728000); // 1728000 ticks = 24 hours
    }

}
