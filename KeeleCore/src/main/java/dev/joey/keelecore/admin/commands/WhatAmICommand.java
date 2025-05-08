package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

public class WhatAmICommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;

        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        Set<String> allowedCommands = new TreeSet<>();

        // 1. Bukkit/Plugin commands via permission nodes
        CommandMap commandMap = getCommandMap();
        if (commandMap == null) {
            player.sendMessage(Component.text("Unable to load command map."));
            return true;
        }

        for (Command cmd : commandMap.getKnownCommands().values()) {
            String requiredPermission = cmd.getPermission();

            if (requiredPermission == null || requiredPermission.isEmpty() || player.hasPermission(requiredPermission)) {
                allowedCommands.add("/" + cmd.getLabel());
            }
        }

        // 2. SuperCommand-based commands via @RequireRank
        Reflections reflections = new Reflections("dev.joey.keelecore.admin.commands");
        Set<Class<? extends SuperCommand>> commandClasses = reflections.getSubTypesOf(SuperCommand.class);
        for (Class<? extends SuperCommand> clazz : commandClasses) {
            RequireRank requireRank = clazz.getAnnotation(RequireRank.class);
            String command1 = "/" + clazz.getSimpleName().replace("Command", "").toLowerCase();
            if (requireRank == null) {
                allowedCommands.add(command1);
                continue;
            }

            if (keelePlayer.getRank().hasPermissionLevel(requireRank.value())) {
                allowedCommands.add(command1);
            }
        }

        // Output result
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