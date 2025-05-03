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

@RequireRank(PlayerRank.ADMIN)

public class GodCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        if (!RankGuard.hasRequiredRank(this, keelePlayer)) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        if (args.length == 0) {
            if (!player.isInvulnerable()) {
                player.setInvulnerable(true);
                UtilClass.sendPlayerMessage(player, "Godmode Enabled", UtilClass.success);
            } else {
                player.setInvulnerable(false);
                UtilClass.sendPlayerMessage(player, "Godmode Disabled", UtilClass.error);
            }
            return true;

        }

        if (args.length == 1) {

            Player victim = Bukkit.getPlayer(args[0]);
            if (playerNullCheck(args[0], player)) return true;
            assert victim != null;
            if (!victim.isInvulnerable()) {
                victim.setInvulnerable(true);
                UtilClass.sendPlayerMessage(player, "Enabled godmode for " + victim.getName(), UtilClass.success);
                UtilClass.sendPlayerMessage(victim, "Godmode Enabled", UtilClass.success);
            } else {
                victim.setInvulnerable(false);
                UtilClass.sendPlayerMessage(player, "Disabled godmode for " + victim.getName(), UtilClass.error);
                UtilClass.sendPlayerMessage(victim, "Godmode Disabled", UtilClass.error);
            }
            return true;
        }
        return false;
    }

}
