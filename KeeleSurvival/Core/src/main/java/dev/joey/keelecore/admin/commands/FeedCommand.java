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

public class FeedCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.feed"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }

        if (args.length == 0) {
            player.setFoodLevel(40);
            player.sendMessage(Component.text().content("Fed " + player.getName()).color(TextColor.color(UtilClass.success)));
            return true;
        }

        if (args.length == 1) {

            Player victim = Bukkit.getPlayer(args[0]);
            if (playerNullCheck(victim, player)) return true;

            assert victim != null;
            victim.setFoodLevel(40);
            player.sendMessage(Component.text().content("Fed " + victim.getName()).color(TextColor.color(UtilClass.success)));
            return true;
        }
        return false;
    }
}
