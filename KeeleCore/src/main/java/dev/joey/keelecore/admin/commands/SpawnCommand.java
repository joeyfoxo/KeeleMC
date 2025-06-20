package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpawnCommand extends SuperCommand  {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        World world = Bukkit.getWorld(UUID.fromString(KeeleCore.getInstance().getConfig().getString("spawnWorld")));
        player.teleport(world.getSpawnLocation().toCenterLocation());
        UtilClass.sendPlayerMessage(player, "Teleported to spawn", UtilClass.success);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}
