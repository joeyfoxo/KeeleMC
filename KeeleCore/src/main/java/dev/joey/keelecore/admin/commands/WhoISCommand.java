package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@RequireRank(PlayerRank.DEV)
public class WhoISCommand extends SuperCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        if (args.length == 1) {
            Player victim = Bukkit.getPlayer(args[0]);
            if (playerNullCheck(victim, player)) return true;

            double x = Math.round(victim.getLocation().getX());
            double y = Math.round(victim.getLocation().getY());
            double z = Math.round(victim.getLocation().getZ());

            UtilClass.sendPlayerMessage(player, "IP: " + victim.getAddress().getAddress()
                    + "\n" + "Last Login: " + getDate(victim.getLastLogin())
                    + "\n" + "Last Seen: " + getDate(victim.getLastSeen())
                    + "\n" + "Last Login: " + "X: " + x + " Y: " + y + " Z: " + z, UtilClass.information);

            return true;

        }
        player.sendMessage(Component.text().content("Invalid Syntax /whois [Player]").color(TextColor.color(UtilClass.error)));
        return false;
    }


    private String getDate(long epoc) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return format.format(epoc);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}
