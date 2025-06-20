package dev.joeyfoxo.keelehub.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.ADMIN)
public class ForceFieldCommand extends SuperCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        KeelePlayer keelePlayer = PlayerPermManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }

        playerNullCheck(player, player);
        if (keelePlayer.isForceFieldEnabled()) { //UNVANISH
            keelePlayer.setForceFieldEnabled(false);
            UtilClass.sendPlayerMessage(player,"Forcefield disabled", UtilClass.error);

        } else {
            keelePlayer.setForceFieldEnabled(true);
            UtilClass.sendPlayerMessage(player, "Forcefield enabled", UtilClass.success);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }

    public static void forceFieldCheck(KeelePlayer center, KeelePlayer victim) {
        Player centerPlayer = center.getPlayer(); // Assuming KeelePlayer has a method to get Bukkit Player
        Player victimPlayer = victim.getPlayer();

        System.out.println("ForceField Check: " + centerPlayer + " vs " + victimPlayer);

        if (centerPlayer == null || victimPlayer == null ||
                victim.getRank().hasPermissionLevel(PlayerRank.ADMIN)) return;

        Location centerLoc = centerPlayer.getLocation();
        Location victimLoc = victimPlayer.getLocation();

        System.out.println("Center Location: " + centerLoc);

        System.out.println("Victim Location: " + victimLoc);

        System.out.println("Distance: " + centerLoc.distance(victimLoc));
        // Check distance <= 4 blocks
        if (centerLoc.distance(victimLoc) <= 4) {
            // Calculate direction from center to victim
            Vector direction = victimLoc.toVector().subtract(centerLoc.toVector()).normalize();

            // Reverse direction to push victim backwards relative to center
            Vector pushBack = direction.multiply(1);

            // Upwards velocity (adjust Y as needed)
            double upwardVelocity = 1.0;

            // Combine upwards and backwards velocity
            Vector velocity = pushBack.multiply(1.5).setY(upwardVelocity);

            // Set the victim's velocity
            victimPlayer.setVelocity(velocity);

            // Play popping sound at victim's location
            victimLoc.getWorld().playSound(victimLoc, Sound.ENTITY_SLIME_SQUISH, 1.0f, 1.0f);
        }
    }
}
