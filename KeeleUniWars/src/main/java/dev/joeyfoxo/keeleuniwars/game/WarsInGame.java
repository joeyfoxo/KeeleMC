package dev.joeyfoxo.keeleuniwars.game;

import dev.joey.keelecore.util.UtilClass;
import dev.joeyfoxo.core.game.CoreGameStatus;
import dev.joeyfoxo.core.game.CoreInGame;
import dev.joeyfoxo.keeleuniwars.generator.WallsGenerator;
import dev.joeyfoxo.keeleuniwars.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WarsInGame<G extends WallsGame<G>> extends CoreInGame<G> {

    private final int wallCountDownMinutes = 2;
    G game;

    public WarsInGame(G game, WallsGenerator wallsGenerator) {
        super(game);
        this.game = game;
        wallCountDown(wallsGenerator);
        gameRunnable();
    }


    public void wallCountDown(WallsGenerator wallsGenerator) {
        new BukkitRunnable() {
            int secondsLeft = wallCountDownMinutes * 60; // Convert minutes to seconds

            @Override
            public void run() {

                if (secondsLeft <= 0) {
                    UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("The walls have dropped!")
                            .color(TextColor.color(UtilClass.information)));
                    wallsGenerator.dropTheWalls();
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

    public void gameRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game.getGameStatus() == CoreGameStatus.IN_GAME) {

                    if (game.getAlivePlayers() <= 1) {
                        UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("Game over!")
                                .color(TextColor.color(UtilClass.information)));
                        game.setGameStatus(CoreGameStatus.FINISHED);
                        Bukkit.getScheduler().runTaskLater(Util.keeleUniWars, () -> Bukkit.spigot().restart(), 20 * 5); // 5 seconds
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Util.keeleUniWars, 0, 20); // Run every second
    }

}