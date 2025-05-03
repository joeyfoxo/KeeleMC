package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequireRank(PlayerRank.MOD)
public class TeleportCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        if (!RankGuard.hasRequiredRank(this, keelePlayer)) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        if (args.length == 1) {
            if (playerNullCheck(args[0], player)) return true;
            Player playerTeleportedTo = Bukkit.getPlayer(args[0]);
            player.teleport(playerTeleportedTo.getLocation());
            UtilClass.sendPlayerMessage(player, "Teleported to " + playerTeleportedTo.getName(), UtilClass.success);
            return true;
        }

        if (args.length == 2) {
            Player playerTeleported = Bukkit.getPlayer(args[0]);
            Player playerTeleportedTo = Bukkit.getPlayer(args[1]);
            if (playerNullCheck(playerTeleported, player) && playerNullCheck(playerTeleportedTo, player)) return true;
            playerTeleported.teleport(playerTeleportedTo);

            UtilClass.sendPlayerMessage(player, "Teleported " + playerTeleported.getName() + " to " + playerTeleportedTo.getName(), UtilClass.success);
            return true;

        }

        return false;
    }
}
