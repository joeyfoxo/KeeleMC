package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;


public class PayCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 2) {

            if (!isAlphanumeric(strings[1], player)) return true;

            double paidAmount = UtilClass.round(Double.parseDouble(strings[1]), 2);
            Player payee = Bukkit.getPlayer(strings[0]);

            if (playerNullCheck(payee, player)) return true;

            if (player == payee) {

                UtilClass.sendPlayerMessage(player, "You can't pay yourself", UtilClass.error);
                return true;
            }

            payPlayer(player, payee, paidAmount);


        } else {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /pay <Player> [amount]", UtilClass.error);
        }
        return false;
    }


    private void payPlayer(Player player, Player payee, double paidAmount) {

        if (!getEconomy().has(player, paidAmount)) {
            UtilClass.sendPlayerMessage(player, "Sorry you don't have sufficient funds", UtilClass.error);
            return;
        }

        Storage.checkAndCreateAccount(payee);
        getEconomy().withdrawPlayer(player, paidAmount);
        getEconomy().depositPlayer(payee, paidAmount);

        UtilClass.sendPlayerMessage(player, "You have sent " + payee.getName() + " " + Storage.getPrefix() + new BigDecimal(paidAmount).toPlainString(), UtilClass.success);
        UtilClass.sendPlayerMessage(payee, player.getName() + " has sent you " + Storage.getPrefix() + new BigDecimal(paidAmount).toPlainString(), UtilClass.success);

    }
}
