package dev.joey.keelesurvival.server.chestprotection.commands;

import dev.joey.keelesurvival.server.chestprotection.ChestLocking;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TrustAccessCommand extends ChestLocking implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 2) {

            Player target = Bukkit.getPlayer(strings[1]);
            if (playerNullCheck(target, player)) return true;

            Block block = player.getTargetBlock(50);
            if (block != null && isLocked(block) && isOwner(player, block)) {

                if (strings[0].equalsIgnoreCase("set") || strings[0].equalsIgnoreCase("add")) {

                    if (addTrustedPlayer(target, block)) {
                        UtilClass.sendPlayerMessage(player, "Trusted " + target.getName() + " to access this chest", UtilClass.success);
                    } else {
                        UtilClass.sendPlayerMessage(player, "Sorry that player couldn't be added", UtilClass.error);
                    }
                }

                if (strings[0].equalsIgnoreCase("remove")) {
                    if (removeTrustedPlayer(target, block)) {
                        UtilClass.sendPlayerMessage(player, "Removed " + target.getName() + "'s access to this chest", UtilClass.success);
                    } else {
                        UtilClass.sendPlayerMessage(player, "Sorry that player couldn't be removed", UtilClass.error);
                    }
                }
            }
            else {
                UtilClass.sendPlayerMessage(player, "The chest must be locked and you must be the owner to set the access", UtilClass.error);
            }
            return true;
        }
        UtilClass.sendPlayerMessage(player, "Invalid Syntax /access set <Player>", UtilClass.error);
        return false;
    }
}
