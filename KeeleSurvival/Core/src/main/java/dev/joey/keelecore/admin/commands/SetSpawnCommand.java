package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class SetSpawnCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        World world = player.getWorld();

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.setspawn"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        keeleCore.getConfig().set("spawnWorld", world.getUID().toString());
        world.setSpawnLocation(player.getLocation());
        UtilClass.sendPlayerMessage(player, "Set spawn", UtilClass.success);

        return false;
    }
}
