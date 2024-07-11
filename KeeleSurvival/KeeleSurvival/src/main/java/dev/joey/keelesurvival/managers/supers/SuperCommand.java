package dev.joey.keelesurvival.managers.supers;

import dev.joey.keelecore.util.UtilClass;
import dev.joey.keelesurvival.server.economy.Storage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuperCommand {

    protected boolean commandSenderCheck(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Sorry you must be a player to do this command!");
            return true;
        }
        return false;
    }

    protected boolean playerNullCheck(Player player, Player sender) {
        if (player == null) {
            UtilClass.sendPlayerMessage(sender, "Sorry thats not a valid player", UtilClass.error);
            return true;
        }
        return false;
    }

    protected boolean playerNullCheck(String player, Player sender) {
        if (Bukkit.getPlayer(player) == null) {
            UtilClass.sendPlayerMessage(sender, "Sorry thats not a valid player", UtilClass.error);
            return true;
        }
        return false;
    }

    protected boolean isAlphanumeric(String amount, Player sender) {
        if (Storage.isValidAmount(amount)) {
            return true;
        } else {
            UtilClass.sendPlayerMessage(sender, "Sorry that's not a valid amount", UtilClass.error);
            return false;
        }
    }

    protected boolean noPermission(Player player, String... permissions) {

        for (String perms : permissions) {

            if (!player.hasPermission(perms)) {
                UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
                return true;
            }

        }
        return false;

    }
}
