package dev.joey.keelesurvival.server.chestprotection.commands;

import dev.joey.keelesurvival.server.chestprotection.ChestLocking;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChestUnlockingCommand extends ChestLocking implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length != 0) {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /unlock (Whilst looking at a chest)", UtilClass.error);

        }

        Block block = player.getTargetBlock(50);

        if (!(block.getType() == Material.CHEST)) {
            UtilClass.sendPlayerMessage(player, "You may only lock or unlock a chest", UtilClass.error);
            return true;
        }

        if (isLocked(block)) {
            unlockChest(player, block);
        } else {
            UtilClass.sendPlayerMessage(player, "Sorry this chest is already unlocked", UtilClass.error);
        }

        return false;
    }
}
