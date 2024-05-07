package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;

public class BalanceCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            getBalance(player, player);
            return true;
        }

        if (strings.length == 1) {

            Player specifiedPlayer = Bukkit.getPlayer(strings[0]);
            if (playerNullCheck(specifiedPlayer, player)) return true;

            getBalance(player, specifiedPlayer);
            return true;
        } else {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /bal <Player>", UtilClass.error);
        }

        return false;
    }

    private void getBalance(Player sender, Player player) {
        Storage.checkAndCreateAccount(player);

        sender.sendMessage(Component.text()
                .content("Balance: ").color(TextColor.color(UtilClass.information)).build()
                .append(Component.text(Storage.getPrefix() + BigDecimal.valueOf(getEconomy().getBalance(player)).toPlainString())
                        .color(TextColor.color(UtilClass.success))).toBuilder().build());

    }

}
