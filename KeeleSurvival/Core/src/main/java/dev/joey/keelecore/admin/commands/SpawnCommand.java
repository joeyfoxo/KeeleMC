package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class SpawnCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        World world = Bukkit.getWorld(UUID.fromString(keeleCore.getConfig().getString("spawnWorld")));
        player.teleport(world.getSpawnLocation().toCenterLocation());
        UtilClass.sendPlayerMessage(player, "Teleported to spawn", UtilClass.success);

        return false;
    }
}
