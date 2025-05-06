package dev.joey.keelesurvival.server.bounties;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;

public class BountyCommand extends SuperCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.openInventory(Bounty.bountyInventory);
            return true;
        }

        if (strings.length == 3) {

            Player target = Bukkit.getPlayer(strings[1]);
            if (playerNullCheck(target, player)) return true;
            if (!isAlphanumeric(strings[2], player)) return true;

            double bountyAmount = Double.parseDouble(strings[2]);

            if (!getEconomy().has(player, bountyAmount)) {
                UtilClass.sendPlayerMessage(player, "Sorry you don't have sufficient funds", UtilClass.error);
                return true;
            }

            if (strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("set")) {

                if (player == target) {
                    UtilClass.sendPlayerMessage(player, "You can't set a bounty on yourself", UtilClass.error);
                    return true;
                }

                if (Bounty.hasBounty(target)) {
                    double newBounty = Bounty.getBounty(target.getUniqueId()) + bountyAmount;
                    Bounty.setBounty(target.getUniqueId(), newBounty);
                    UtilClass.sendPlayerMessage(player, "Bounty increased to " + Storage.getPrefix() + newBounty + " for " + target.getName(), UtilClass.success);

                } else {
                    Bounty.setBounty(target.getUniqueId(), bountyAmount);
                    UtilClass.sendPlayerMessage(player, "Bounty set on "
                            + target.getName() + " for "
                            + Storage.getPrefix() + bountyAmount, UtilClass.success);

                    if (bountyAmount > 10000) {
                        Bukkit.broadcast(Component.text().content(player.getName() + " has set a " +
                                        Storage.getPrefix() + bountyAmount + " bounty on " + target.getName())
                                .decorate(TextDecoration.UNDERLINED)
                                .color(TextColor.color(0, 227, 255)).build());
                    } else {
                        UtilClass.sendPlayerMessage(target, player.getName() + " has set a " +
                                Storage.getPrefix() + bountyAmount + " bounty on you", UtilClass.information);
                    }
                }

                getEconomy().withdrawPlayer(player, bountyAmount);
                return true;

            }
        }

        if (strings.length == 2) {

            if (strings[0].equalsIgnoreCase("get")) {

                Player target = Bukkit.getPlayer(strings[1]);
                if (playerNullCheck(target, player)) return true;

                if (Bounty.hasBounty(target)) {
                    UtilClass.sendPlayerMessage(player, target.getName() + " has a " + Storage.getPrefix() + Bounty.getBounty(target.getUniqueId()) + " bounty on their head!", UtilClass.success);

                } else {
                    UtilClass.sendPlayerMessage(player, "That player doesn't have a bounty on them", UtilClass.error);
                }
                return true;
            }

            if (strings[0].equalsIgnoreCase("remove")) {

                KeelePlayer ke = PermissionManager.getCached(player.getUniqueId());

                if (!noPermission(ke, PlayerRank.ADMIN)) return true;

                Player target = Bukkit.getPlayer(strings[1]);

                if (Bounty.hasBounty(target)) {
                    Bounty.removeBounty(target.getUniqueId());
                    BountyListener.sortedBounties.remove(target.getUniqueId());
                    UtilClass.sendPlayerMessage(player, "Removed bounty from " + target.getName(), UtilClass.success);

                } else {
                    UtilClass.sendPlayerMessage(player, "That player doesn't have a bounty on them", UtilClass.error);
                }
                return true;

            }
        }

        UtilClass.sendPlayerMessage(player, "Invalid Syntax \n /bounty [set/add] <Player> <Amount \n /bounty [get] <Player> \n /bounty", UtilClass.error);
        return false;
    }
}