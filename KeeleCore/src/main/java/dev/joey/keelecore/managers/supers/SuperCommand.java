package dev.joey.keelecore.managers.supers;

import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class SuperCommand implements CommandExecutor, TabCompleter {

    protected boolean commandSenderCheck(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Sorry you must be a player to do this command!");
            return true;
        }
        return false;
    }

    protected boolean playerNullCheck(Player player, Player sender) {
        if (player == null) {
            UtilClass.sendPlayerMessage(sender, "Sorry that's not a valid player", UtilClass.error);
            return true;
        }
        return false;
    }

    protected boolean playerNullCheck(String playerName, Player sender) {
        if (Bukkit.getPlayer(playerName) == null) {
            UtilClass.sendPlayerMessage(sender, "Sorry that's not a valid player", UtilClass.error);
            return true;
        }
        return false;
    }
    protected  boolean noAccessMessage(Object instance, KeelePlayer keelePlayer) {
        return RankGuard.hasRequiredRank(instance, keelePlayer);
    }
}