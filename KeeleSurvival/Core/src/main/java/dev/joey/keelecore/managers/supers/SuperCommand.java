package dev.joey.keelecore.managers.supers;

import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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
}
