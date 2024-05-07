package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnounceCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.announce"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }


        if (strings.length >= 1) {

            StringBuilder stringBuilder = new StringBuilder();

            for (String words : new ArrayList<>(List.of(strings))) {
                stringBuilder.append(words).append(" ");
            }

            Bukkit.broadcast(Component.text()
                    .content(ChatColor.translateAlternateColorCodes('&', "&a&lAnnouncement&7> &r" + stringBuilder)).build());
        return true;
        }

        UtilClass.sendPlayerMessage(player, "Invalid Syntax /announce <Message>", UtilClass.error);


        return false;
    }
}
