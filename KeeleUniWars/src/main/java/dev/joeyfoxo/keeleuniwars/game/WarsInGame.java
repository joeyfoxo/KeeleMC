package dev.joeyfoxo.keeleuniwars.game;

import dev.joey.keelecore.util.UtilClass;
import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.CoreInGame;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.keeleuniwars.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static dev.joeyfoxo.keeleuniwars.game.Settings.wallCountDownMinutes;
import static dev.joeyfoxo.keeleuniwars.generator.WallsGenerator.*;

public class WarsInGame extends CoreGame<WallsGame> {

    WallsGame game;

    public WarsInGame(WallsGame game, World world) {
        this.game = game;
        wallCountDown(world);
    }

    public void wallCountDown(World world) {
        spawnMobs(world);
        new BukkitRunnable() {
            int secondsLeft = wallCountDownMinutes * 60; // Convert minutes to seconds

            @Override
            public void run() {

                if (secondsLeft <= 0) {
                    UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("The walls are dropping!")
                            .color(TextColor.color(UtilClass.information)));
                    dropTheWalls(world);
                    cancel();
                    return;
                }

                int minutes = secondsLeft / 60;
                int seconds = secondsLeft % 60;

                // Display the countdown in the action bar for all players
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text("The walls will drop in " + minutes + " minutes " + seconds + " seconds")
                            .color(TextColor.color(UtilClass.information)));
                }

                if (seconds == 0) { // Only show the countdown in chat in minutes
                    if (minutes == 1) {
                        UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("The walls will drop in 1 minute")
                                .color(TextColor.color(UtilClass.information)));
                    } else {
                        UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("The walls will drop in " + minutes + " minutes")
                                .color(TextColor.color(UtilClass.information)));
                    }
                }

                secondsLeft--;
            }
        }.runTaskTimer(Util.keeleUniWars, 0, 20); // Run every second
    }

    public void dropTheWalls(World world) {
        new BukkitRunnable() {
            int y = world.getMaxHeight() - 1; // Start from the top layer

            @Override
            public void run() {
                if (y < world.getMinHeight()) {
                    //If this fails to run its fucked.
                    this.cancel(); // Stop the task when all layers have been removed
                    return;
                }

                for (Location location : wallsLocation.keySet()) {

                    if (outerWallLocation.contains(location)) {
                        continue;
                    }

                    if (surfaceBlockLocation.contains(location)) {
                        Block surfaceBlock = wallsLocation.get(location);

                        //TODO: Fix this
                        if (surfaceBlock.getY() == y) {
                            surfaceBlock.setType(Material.GRASS_BLOCK);
                        } else if (surfaceBlock.getY() < y && surfaceBlock.getY() > world.getMinHeight()) {
                            surfaceBlock.setType(Material.DIRT);
                        }
                        continue;
                    }

                    Block block = wallsLocation.get(location);
                    if (block.getY() == y) {
                        block.setType(Material.AIR);
                    }
                }
                y--; // Move to the next layer
            }
        }.runTaskTimer(Core.getKeeleMiniCore(), 0L, 1); // Schedule the task to run every second (20 ticks)
        game.setGameStatus(GameStatus.IN_GAME);
    }

    private void spawnMobs(World world) {
        List<EntityType> passiveMobs = Arrays.asList(
                EntityType.COW,
                EntityType.SHEEP,
                EntityType.PIG,
                EntityType.CHICKEN
        );

        Random random = new Random();
        int radius = 50;
        int mobCountPerPlayer = 10;

        for (Player player : Bukkit.getOnlinePlayers()) {
            Location playerLocation = player.getLocation();

            for (int i = 0; i < mobCountPerPlayer; i++) {
                // Calculate random offset within radius for each mob
                double xOffset = (random.nextDouble() * 2 - 1) * radius;
                double zOffset = (random.nextDouble() * 2 - 1) * radius;

                Location spawnLocation = playerLocation.clone().add(xOffset, 0, zOffset);
                spawnLocation.setY(world.getHighestBlockYAt(spawnLocation) + 1);

                // Select a random mob type
                EntityType mobType = passiveMobs.get(random.nextInt(passiveMobs.size()));

                // Spawn the selected mob at the spawn location
                world.spawnEntity(spawnLocation, mobType);
            }
        }
    }

}