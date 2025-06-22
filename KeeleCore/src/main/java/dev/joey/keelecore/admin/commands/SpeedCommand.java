package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.DEV)
public class SpeedCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }


        if (args.length >= 1) {

            float speed;
            try {
                speed = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                UtilClass.sendPlayerMessage(player, "Please enter a valid number.", UtilClass.error);
                return true;
            }

            if (speed >= -1.0f && speed <= 1.0f) {
                player.setFlySpeed(speed);
                UtilClass.sendPlayerMessage(player, "Flight speed set to " + speed, UtilClass.success);
            } else {
                UtilClass.sendPlayerMessage(player, "Value must be between -1 and 1", UtilClass.error);
            }
            return true;
        }

        UtilClass.sendPlayerMessage(player, "Invalid Syntax /speed <Speed>", UtilClass.error);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("0.1", "0.3", "0.5", "0.8", "1.0");
        }
        return List.of();
    }
}
