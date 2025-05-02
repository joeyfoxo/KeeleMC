package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

@RequireRank(PlayerRank.DEV)
public class SetSpawnCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        World world = player.getWorld();

        KeelePlayer keelePlayer = PermissionManager.get(player.getUniqueId());
        if (!RankGuard.hasRequiredRank(this, keelePlayer)) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        keeleCore.getConfig().set("spawnWorld", world.getUID().toString());
        world.setSpawnLocation(player.getLocation());
        UtilClass.sendPlayerMessage(player, "Set spawn", UtilClass.success);

        return false;
    }
}
