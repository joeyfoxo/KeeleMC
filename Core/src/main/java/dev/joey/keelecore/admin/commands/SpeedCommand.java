package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpeedCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.speed"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }


        if (args.length >= 1) {

            if (!(Float.parseFloat(args[0]) > 1 || Float.parseFloat(args[0]) < -1)) {

                float speed = Float.parseFloat(args[0]);

                player.setFlySpeed(speed);

                UtilClass.sendPlayerMessage(player, "Flight speed set to " + speed, UtilClass.success);
            }

            else {
                UtilClass.sendPlayerMessage(player, "Value must be between -1 and 1", UtilClass.error);
            }
            return true;
        }

        UtilClass.sendPlayerMessage(player, "Invalid Syntax /speed <Speed>", UtilClass.error);
        return false;
    }
}
