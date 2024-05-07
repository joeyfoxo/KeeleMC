package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.Util.keeleSurvivalNameSpace;


public class WithdrawCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 1) {

            if (!isAlphanumeric(strings[0], player)) return true;

            double paidAmount = UtilClass.round(Double.parseDouble(strings[0]), 2);
            withdrawAmount(player, paidAmount);

        } else {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /withdraw <amount>", UtilClass.error);
        }
        return false;
    }


    private void withdrawAmount(Player player, double paidAmount) {

        if (!getEconomy().has(player, paidAmount)) {
            UtilClass.sendPlayerMessage(player, "Sorry you don't have sufficient funds", UtilClass.error);
            return;
        }

        getEconomy().withdrawPlayer(player, paidAmount);

        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text("Bank Note: " + paidAmount)
                .color(TextColor.color(217, 181, 0))
                .decoration(TextDecoration.ITALIC, false));

        meta.getPersistentDataContainer().set(new NamespacedKey(keeleSurvivalNameSpace, "bank_note"),
                PersistentDataType.DOUBLE, paidAmount);

        itemStack.setItemMeta(meta);
        player.getInventory().addItem(itemStack);

        UtilClass.sendPlayerMessage(player, "You have withdrawn " + Storage.getPrefix() + new BigDecimal(paidAmount).toPlainString(), UtilClass.success);

    }
}
