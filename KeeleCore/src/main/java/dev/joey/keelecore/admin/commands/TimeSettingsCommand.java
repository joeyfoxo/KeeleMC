package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequireRank(PlayerRank.DEV)
public class TimeSettingsCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {

        if (args.length == 1) {
            return Stream.of("set", "add")
                    .filter(opt -> opt.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {

            // Add named times
            List<String> suggestions = new ArrayList<>(UtilClass.TimesTickFormat.nameToTicks.keySet());
            return suggestions.stream()
                    .filter(opt -> opt.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}