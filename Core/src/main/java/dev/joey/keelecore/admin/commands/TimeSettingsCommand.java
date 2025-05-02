package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@RequireRank(PlayerRank.DEV)
public class TimeSettingsCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        KeelePlayer keelePlayer = PermissionManager.get(player.getUniqueId());
        if (!RankGuard.hasRequiredRank(this, keelePlayer)) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(Component.text().content("Invalid Syntax").color(TextColor.color(UtilClass.error)));
            player.sendMessage(Component.text().content("/time <set|add> [time]").color(TextColor.color(UtilClass.error)));
            return true;
        }

        World world = player.getWorld();

        if (args[1].matches("^[0-9]*$")) {

            if (Long.parseLong(args[1]) >= 24000) {
                UtilClass.sendPlayerMessage(player, args[1] + " is not a valid time of day", UtilClass.error);
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                world.setTime(Long.parseLong(args[1]));
                player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(UtilClass.success)));
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                world.setTime(world.getTime() + Long.parseLong(args[1]));
                player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(UtilClass.success)));
                return true;
            }
        } else {


            if (!UtilClass.TimesTickFormat.nameToTicks.containsKey(args[1])) {
                UtilClass.sendPlayerMessage(player, args[1] + " is not a valid time of day", UtilClass.error);
                UtilClass.sendPlayerMessage(player, "Try one of these - " + new ArrayList<>(UtilClass.TimesTickFormat.nameToTicks.keySet()), UtilClass.error);
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                world.setTime(UtilClass.TimesTickFormat.nameToTicks.get(args[1]));
                player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(UtilClass.success)));
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                world.setTime(world.getTime() + UtilClass.TimesTickFormat.nameToTicks.get(args[1]));
                player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(UtilClass.success)));
                return true;
            }
        }
        return false;
    }
}