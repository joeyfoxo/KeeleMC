package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.MOD)
public class TeleportCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;
        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        try {
            switch (args.length) {
                case 1 -> {
                    // /teleport <player>
                    if (playerNullCheck(args[0], player)) return true;
                    Player target = Bukkit.getPlayer(args[0]);
                    player.teleport(target.getLocation());
                    UtilClass.sendPlayerMessage(player, "Teleported to " + target.getName(), UtilClass.success);
                }

                case 2 -> {
                    // /teleport <player1> <player2>
                    Player p1 = Bukkit.getPlayer(args[0]);
                    Player p2 = Bukkit.getPlayer(args[1]);
                    if (playerNullCheck(p1, player) || playerNullCheck(p2, player)) return true;
                    p1.teleport(p2);
                    UtilClass.sendPlayerMessage(player, "Teleported " + p1.getName() + " to " + p2.getName(), UtilClass.success);
                }

                case 3 -> {
                    // /teleport <x> <y> <z>
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);
                    player.teleport(player.getLocation().set(x, y, z));
                    UtilClass.sendPlayerMessage(player, "Teleported to coordinates", UtilClass.success);
                }

                case 4 -> {
                    // /teleport <player> <x> <y> <z>
                    Player target = Bukkit.getPlayer(args[0]);
                    if (playerNullCheck(target, player)) return true;

                    double x = Double.parseDouble(args[1]);
                    double y = Double.parseDouble(args[2]);
                    double z = Double.parseDouble(args[3]);

                    target.teleport(target.getLocation().set(x, y, z));
                    UtilClass.sendPlayerMessage(player, "Teleported " + target.getName() + " to coordinates", UtilClass.success);
                }

                default -> {
                    UtilClass.sendPlayerMessage(player, "Usage:\n/teleport <player>\n/teleport <x> <y> <z>\n/teleport <player1> <player2>\n/teleport <player> <x> <y> <z>", UtilClass.error);
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            UtilClass.sendPlayerMessage(player, "Invalid coordinate format. Please use numbers.", UtilClass.error);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            // /teleport <player OR x>
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {
            // /teleport <player1> <player2> OR /teleport <x> <y>
            if (isNumeric(args[0])) {
                return List.of("64", "100", "128", "0", "-64")
                        .stream().filter(val -> val.startsWith(args[1])).toList();
            } else {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .toList();
            }
        }

        if (args.length == 3 || args.length == 4) {
            // /teleport <x> <y> <z> OR /teleport <player> <x> <y> <z>
            return List.of("0", "64", "128", "256", "-64")
                    .stream().filter(val -> val.startsWith(args[args.length - 1])).toList();
        }

        return List.of();
    }
    private boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
