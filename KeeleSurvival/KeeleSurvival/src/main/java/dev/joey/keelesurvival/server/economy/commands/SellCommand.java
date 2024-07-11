package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.SellableItems;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class SellCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            UtilClass.sendPlayerMessage(player, "You must have an item in your hand to sell", UtilClass.error);
            return true;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {

            ItemStack head = player.getInventory().getItemInMainHand();
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            if (meta.getOwningPlayer() == player) {
                UtilClass.sendPlayerMessage(player, "You can't sell your own head", UtilClass.error);
                return true;
            }

            if (!meta.hasCustomModelData()) {
                KeeleSurvival.getEconomy().depositPlayer(player, 200);
                UtilClass.sendPlayerMessage(player, "Sold head for £200", UtilClass.success);
                player.getInventory().setItemInMainHand(null);
                return true;
            }
            KeeleSurvival.getEconomy().depositPlayer(player, meta.getCustomModelData());
            UtilClass.sendPlayerMessage(player, "Sold head for £" + meta.getCustomModelData(), UtilClass.success);
            player.getInventory().setItemInMainHand(null);
            return true;
        }

        if (!SellableItems.isSellable(player.getInventory().getItemInMainHand().getType())) {
            UtilClass.sendPlayerMessage(player, "That item isn't sellable", UtilClass.error);
            return true;
        }

        SellableItems.sellItem(player.getInventory().getItemInMainHand().getType(), player);






        return false;
    }
}
