package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.MOD)
public class UnbanCommand extends SuperCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        KeelePlayer kPlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, kPlayer)) return true;

        if (args.length != 1) {
            UtilClass.sendPlayerMessage(kPlayer, "Usage: /unban <player>", UtilClass.error);
            return true;
        }

        String targetName = args[0];
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        if (banList.getBanEntry(targetName) == null) {
            UtilClass.sendPlayerMessage(kPlayer, targetName + " is not banned.", UtilClass.error);
            return true;
        }

        banList.pardon(targetName);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}