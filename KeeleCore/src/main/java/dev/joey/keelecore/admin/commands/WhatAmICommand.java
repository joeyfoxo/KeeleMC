package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

@RequireRank(PlayerRank.HELPER)
public class WhatAmICommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;

        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        CommandMap commandMap = getCommandMap();
        if (commandMap == null) {
            player.sendMessage(Component.text("Unable to load command map."));
            return true;
        }

        System.out.println(keelePlayer.getRank().name());
        System.out.println(keelePlayer.getRank().getPermissions());

        Set<String> allowedCommands = new TreeSet<>();

        for (Command cmd : commandMap.getKnownCommands().values()) {
            String requiredPermission = cmd.getPermission();

            if (requiredPermission == null || requiredPermission.isEmpty() || player.hasPermission(requiredPermission)) {
                allowedCommands.add("/" + cmd.getLabel());
            }
        }

        // Output to player
        if (allowedCommands.isEmpty()) {
            player.sendMessage(Component.text("No commands available for your rank."));
        } else {
            player.sendMessage(Component.text("Commands you can use:"));
            for (String cmd : allowedCommands) {
                player.sendMessage(Component.text("- " + cmd));
            }
        }

        return true;
    }

    private CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}