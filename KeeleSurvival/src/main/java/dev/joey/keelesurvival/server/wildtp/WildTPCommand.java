package dev.joey.keelesurvival.server.wildtp;

import dev.joey.keelecore.util.UtilClass;
import dev.joey.keelesurvival.managers.supers.SuperCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class WildTPCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        double worldBorderSize = player.getWorld().getWorldBorder().getSize();

        double x = ThreadLocalRandom.current().nextDouble(0, worldBorderSize / 3);
        double z = ThreadLocalRandom.current().nextDouble(0, worldBorderSize / 3);

        World world = player.getWorld();

        // Preload the chunk
        world.getChunkAt(new Location(world, x, world.getHighestBlockYAt((int) x, (int) z) + 1, z)).load();

        UtilClass.sendPlayerMessage(player, "Teleporting!", UtilClass.success);
        Bukkit.getScheduler().runTaskLater(keeleSurvival, () -> {
            player.teleport(new Location(world, x, world.getHighestBlockYAt((int) x, (int) z) + 1, z));
        }, 100L);

        return false;
    }
}