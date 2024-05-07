package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModeCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.gamemode"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            player.sendMessage(Component.text().content("Invalid Syntax").color(TextColor.color(UtilClass.error)));
            player.sendMessage(Component.text().content("/gamemode <type> [player]").color(TextColor.color(UtilClass.error)));
            return true;
        }

        if (args.length == 2) {
            if (playerNullCheck(args[1], player)) return true;
            player = Bukkit.getPlayer(args[1]);
        }

        changeGameMode(player, args);

        return false;
    }

    private void changeGameMode(Player player, String[] args) {

        switch (args[0]) {
            case "0", "survival" -> {
                player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                UtilClass.sendPlayerMessage(player, "Gamemode changed to " + player.getGameMode(), UtilClass.success);
                return;
            }
            case "1", "creative" -> {
                player.setGameMode(org.bukkit.GameMode.CREATIVE);

                UtilClass.sendPlayerMessage(player, "Gamemode changed to " + player.getGameMode(), UtilClass.success);
                return;
            }
            case "2", "adventure" -> {
                player.setGameMode(org.bukkit.GameMode.ADVENTURE);
                UtilClass.sendPlayerMessage(player, "Gamemode changed to " + player.getGameMode(), UtilClass.success);
                return;
            }
            case "3", "spectator" -> {
                player.setGameMode(org.bukkit.GameMode.SPECTATOR);
                UtilClass.sendPlayerMessage(player, "Gamemode changed to " + player.getGameMode(), UtilClass.success);
                return;
            }
        }
        UtilClass.sendPlayerMessage(player, "Sorry that's not a valid gamemode", UtilClass.error);

    }
}
