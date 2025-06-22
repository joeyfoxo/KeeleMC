package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequireRank(PlayerRank.MOD)
public class BanCommand extends SuperCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player spigotPlayer = (Player) sender;
        KeelePlayer keelePlayer = PlayerPermManager.getCached(spigotPlayer.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) return true;

        if (args.length < 2) {
            UtilClass.sendPlayerMessage(keelePlayer, "Usage: /ban [-s] [-ip] <player> <reason>", UtilClass.error);
            return true;
        }

        // ✅ Step 1: Collect flags
        boolean silent = false;
        boolean ipBan = false;
        String targetName = null;
        List<String> reasonParts = new ArrayList<>();

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-s")) {
                silent = true;
            } else if (arg.equalsIgnoreCase("-ip")) {
                ipBan = true;
            } else if (targetName == null) {
                targetName = arg;
            } else {
                reasonParts.add(arg);
            }
        }

        if (targetName == null) {
            UtilClass.sendPlayerMessage(keelePlayer, "No player specified.", UtilClass.error);
            return true;
        }

        String reason = reasonParts.isEmpty() ? "No reason provided." : String.join(" ", reasonParts);
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (playerNullCheck(targetName, spigotPlayer)) return true;

        // ✅ Ban logic
        if (ipBan) {
            if (target.isOnline()) {
                Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                if (onlineTarget != null && onlineTarget.getAddress() != null) {
                    String ip = onlineTarget.getAddress().getAddress().getHostAddress();
                    Bukkit.getBanList(BanList.Type.IP).addBan(ip, reason, null, sender.getName());
                    onlineTarget.kick(Component.text("You have been IP banned.\nReason: " + reason));
                } else {
                    UtilClass.sendPlayerMessage(keelePlayer, "Could not retrieve IP address.", UtilClass.error);
                    return true;
                }
            } else {
                UtilClass.sendPlayerMessage(keelePlayer, "Target must be online for an IP ban.", UtilClass.error);
                return true;
            }
        } else {
            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, sender.getName());
            if (target.isOnline()) {
                ((Player) target).kick(Component.text("You have been banned.\nReason: " + reason + "\n" +
                        "Banned by: " + sender.getName()));
            }
        }

        if (!silent) {
            Bukkit.broadcast(Component.text("✖ " + target.getName() + " has been banned by " + sender.getName() +
                    (ipBan ? " (IP ban)" : "") + " for: " + reason));
        } else {
            sender.sendMessage("✔ Silently banned " + target.getName() + (ipBan ? " (IP)" : "") + ".");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {
            return List.of("-s", "-ip");
        }

        return Collections.emptyList();
    }
}