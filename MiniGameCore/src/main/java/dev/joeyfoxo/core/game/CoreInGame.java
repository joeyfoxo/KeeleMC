package dev.joeyfoxo.core.game;

import dev.joey.keelecore.util.UtilClass;
import dev.joeyfoxo.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CoreInGame<G extends CoreGame<G>> {

    G game;

    public CoreInGame(G game) {
        this.game = game;
        gameRunnable();
    }

    public void gameRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (game.getGameStatus() != GameStatus.WAITING || game.getGameStatus() != GameStatus.NOT_READY) {
                    if (game.isTeamed()) {

                        if (game.getTeamsAlive().size() <= 1) {
                            UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("Game over!")
                                    .color(TextColor.color(UtilClass.information)));
                            game.setGameStatus(GameStatus.FINISHED);
                        }
                    } else {

                        //if (game.getAlivePlayerCount() <= 1) {
                        if (game.getAlivePlayerCount() < 1) {
                            UtilClass.sendPlayerMessage(Bukkit.getOnlinePlayers(), Component.text("Game over!")
                                    .color(TextColor.color(UtilClass.information)));
                            game.setGameStatus(GameStatus.FINISHED);
                        }
                    }
                }
                if (game.getGameStatus() == GameStatus.FINISHED) {
                    Bukkit.getScheduler().runTaskLater(Core.getKeeleMiniCore(), Bukkit::shutdown, 20 * 5); // 5 seconds
                    cancel();
                }
            }
        }.runTaskTimer(Core.getKeeleMiniCore(), 0, 20); // Run every second
    }

}
