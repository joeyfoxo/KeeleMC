package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequireRank(PlayerRank.ADMIN)
public class RankCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        KeelePlayer executor = PermissionManager.getCached(player.getUniqueId());

        if (UtilClass.noAccessMessage(this, executor)) {
            return true;
        }

        if (args.length < 1) {
            UtilClass.sendPlayerMessage(executor, "Usage: /rank <debug|set|remove> ...", UtilClass.error);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "debug" -> {
                if (args.length != 2) {
                    UtilClass.sendPlayerMessage(executor, "Usage: /rank debug <rank>", UtilClass.error);
                    return true;
                }
                PlayerRank rank = PlayerRank.fromString(args[1]);
                if (rank == null) {
                    UtilClass.sendPlayerMessage(executor, "Invalid rank. Available ranks: " + PlayerRank.listRanks(), UtilClass.error);
                    return true;
                }
                PermissionManager.setRank(player, rank);
                UtilClass.sendPlayerMessage(executor, "Your rank has been set to " + rank.name(), UtilClass.success);
            }

            case "set" -> {

                if (args.length != 3) {
                    UtilClass.sendPlayerMessage(executor, "Usage: /rank set <player> <rank>", UtilClass.error);
                    return true;
                }

                String targetName = args[1];
                String rankInput = args[2];

                Player targetPlayer = executor.getPlayer().getServer().getPlayerExact(targetName);
                if (targetPlayer == null) {
                    UtilClass.sendPlayerMessage(executor, "Player '" + targetName + "' not found or offline.", UtilClass.error);
                    return true;
                }

                KeelePlayer target = PermissionManager.getCached(targetPlayer.getUniqueId());
                PlayerRank newRank = PlayerRank.fromString(rankInput);
                if (newRank == null) {
                    UtilClass.sendPlayerMessage(executor, "Invalid rank. Available ranks: " + PlayerRank.listRanks(), UtilClass.error);
                    return true;
                }

                if (!executor.getRank().hasPermissionLevel(newRank)) {
                    UtilClass.sendPlayerMessage(executor, "You cannot assign a rank higher than your own.", UtilClass.error);
                    return true;
                }

                PermissionManager.setRank(targetPlayer, newRank);
                UtilClass.sendPlayerMessage(target, "Your rank has been set to " + newRank.name(), UtilClass.success);
                UtilClass.sendPlayerMessage(executor, "Set " + target.getName() + "'s rank to " + newRank.name(), UtilClass.success);
            }

            case "remove" -> {

                if (UtilClass.noAccessMessage(this, executor)) {
                    return true;
                }

                if (args.length != 2) {
                    UtilClass.sendPlayerMessage(executor, "Usage: /rank remove <player>", UtilClass.error);
                    return true;
                }

                String targetName = args[1];
                Player targetPlayer = executor.getPlayer().getServer().getPlayerExact(targetName);
                if (targetPlayer == null) {
                    UtilClass.sendPlayerMessage(executor, "Player '" + targetName + "' not found or offline.", UtilClass.error);
                    return true;
                }

                KeelePlayer target = PermissionManager.getCached(targetPlayer.getUniqueId());
                PermissionManager.setRank(targetPlayer, PlayerRank.PLAYER);
                UtilClass.sendPlayerMessage(target, "Your rank has been reset to PLAYER.", UtilClass.success);
                UtilClass.sendPlayerMessage(executor, "Removed rank from " + target.getName() + ".", UtilClass.success);
            }

            default -> UtilClass.sendPlayerMessage(executor, "Unknown subcommand. Use /rank <debug|set|remove>", UtilClass.error);
        }

        return true;
    }
}