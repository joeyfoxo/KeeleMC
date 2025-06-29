package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequireRank(PlayerRank.ADMIN)
public class AnnounceCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;


        Player spigotPlayer = (Player) commandSender;
        KeelePlayer player = PlayerPermManager.getCached(spigotPlayer.getUniqueId());

        if (UtilClass.noAccessMessage(this, player)) {
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}
