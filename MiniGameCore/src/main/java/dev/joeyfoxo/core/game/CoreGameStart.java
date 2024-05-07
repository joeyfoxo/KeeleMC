package dev.joeyfoxo.core.game;

import dev.joey.keelecore.util.UtilClass;
import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/**
 * CoreGameStart class for the KeeleMC plugin.
 * This class is responsible for starting the game, including a countdown and player readiness checks.
 *
 * @param <G> The type of game this class is managing. Must extend CoreGame.
 */
public class CoreGameStart<G extends CoreGame<G>> {

    protected int countdownMins = 5;
    protected int minimumPlayers = 2;
    private int countdownSeconds = countdownMins * 60; // Convert minutes to seconds
    protected World world = Bukkit.getWorld("world");
    G game;

    /**
     * Constructor for the CoreGameStart class.
     * Initializes the game instance and creates a new CoreSettings instance for the world.
     *
     * @param game The game instance this class will manage.
     */
    public CoreGameStart(G game) {
        this.game = game;
        new CoreSettings<>(game);
    }

    /**
     * Starts the countdown to the game start.
     * The countdown is done on a BukkitRunnable, which is scheduled to run every second.
     * The countdown checks for player readiness and updates player XP bars to reflect the remaining time.
     */
    protected void startCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

                if (onlinePlayers.isEmpty()) {
                    return; // Don't start counting down until there is at least 1 player in the game
                }

                if (onlinePlayers.size() >= minimumPlayers && countdownSeconds > 10) {
                    countdownSeconds = 10; // Set countdown to 10 seconds if there are more than minimum players
                }

                if (onlinePlayers.size() < minimumPlayers && countdownSeconds <= 0) {
                    UtilClass.sendPlayerMessage(onlinePlayers, Component.text("Waiting for more players to join...")
                            .color(TextColor.color(UtilClass.information)));
                    countdownSeconds = countdownMins * 60; // Reset countdown
                    return;
                }

                if (countdownSeconds <= 0) {
                    this.cancel();
                    game.setGameStatus(CoreGameStatus.IN_GAME);

                    // Remove all XP for all online players
                    for (Player player : onlinePlayers) {
                        player.setLevel(0);
                    }

                    onlinePlayers.forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 0.7f));
                    UtilClass.sendPlayerMessage(onlinePlayers, Component.text("Game has started!")
                            .color(TextColor.color(UtilClass.information)));

                    return;
                }

                if (countdownSeconds % 60 == 0 || countdownSeconds <= 10) {
                    UtilClass.sendPlayerMessage(onlinePlayers, Component.text("The game will start in " + (countdownSeconds > 60 ? countdownSeconds / 60 + " minutes" : countdownSeconds + " seconds"))
                            .color(TextColor.color(UtilClass.information)));
                    onlinePlayers.forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.7f));
                }

                // Update the XP bar for all online players
                for (Player player : onlinePlayers) {
                    player.setLevel(countdownSeconds);
                }

                countdownSeconds--;
            }
        }.runTaskTimer(Core.getKeeleMiniCore(), 0, 20); // Run every second
    }

    /**
     * Sets the countdown time in minutes.
     *
     * @param countdownMins The new countdown time in minutes.
     */
    protected void setCountdownMins(int countdownMins) {
        this.countdownMins = countdownMins;
    }

    /**
     * Sets the minimum number of players required to start the game.
     *
     * @param minimumPlayers The new minimum number of players.
     */
    protected void setMinimumPlayers(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    /**
     * Returns the countdown time in minutes.
     *
     * @return The countdown time in minutes.
     */
    protected int getCountdownMins() {
        return countdownMins;
    }

    /**
     * Returns the minimum number of players required to start the game.
     *
     * @return The minimum number of players.
     */
    protected int getMinimumPlayers() {
        return minimumPlayers;
    }
}