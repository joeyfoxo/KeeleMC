package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.DEV)
public class SetSpawnCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        World world = player.getWorld();

        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        KeeleCore.getInstance().getConfig().set("spawnWorld", world.getUID().toString());
        world.setSpawnLocation(player.getLocation());
        UtilClass.sendPlayerMessage(player, "Set spawn", UtilClass.success);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}
