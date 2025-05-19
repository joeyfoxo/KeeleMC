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
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

@RequireRank(PlayerRank.HELPER)
public class WhatAmICommand extends SuperCommand {

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

            if (requiredPermission == null || requiredPermission.isEmpty()) {
                continue; // Skip commands without permissions
            }
            if (player.hasPermission(requiredPermission)) {
                allowedCommands.add("/" + cmd.getLabel());
            }
        }

        // 2. SuperCommand-based commands via @RequireRank
        Reflections reflections = new Reflections("dev.joey.keelecore.admin.commands");
        Set<Class<? extends SuperCommand>> commandClasses = reflections.getSubTypesOf(SuperCommand.class);
        for (Class<? extends SuperCommand> clazz : commandClasses) {
            String command1 = "/" + clazz.getSimpleName().replace("Command", "").toLowerCase();
            RequireRank requireRank = clazz.getAnnotation(RequireRank.class);
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

            // Rediscover the SuperCommand classes for annotation info
            Map<String, PlayerRank> rankMap = new HashMap<>();
            for (Class<? extends SuperCommand> clazz : commandClasses) {
                String name = "/" + clazz.getSimpleName().replace("Command", "").toLowerCase();
                RequireRank rank = clazz.getAnnotation(RequireRank.class);
                if (rank != null) {
                    rankMap.put(name, rank.value());
                }
            }

            for (String cmd : allowedCommands) {
                PlayerRank required = rankMap.get(cmd);
                String colorCode = required != null ? required.getColorCode() : "&7"; // Default to gray

                Component line = LegacyComponentSerializer.legacyAmpersand()
                        .deserialize(colorCode + cmd);

                player.sendMessage(line);
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}