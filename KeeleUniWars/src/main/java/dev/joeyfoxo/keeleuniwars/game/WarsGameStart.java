package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.CoreGameStatus;
import dev.joeyfoxo.core.game.CoreSettings;
import dev.joeyfoxo.keeleuniwars.game.events.CageHandler;
import dev.joeyfoxo.keeleuniwars.generator.WallsGenerator;
import dev.joeyfoxo.keeleuniwars.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import static dev.joeyfoxo.keeleuniwars.generator.WallsGenerator.center;

public class WarsGameStart<G extends WallsGame<G>> extends CoreGameStart<G> {
    G game;

    WallsGenerator wallsGenerator = new WallsGenerator();

    public WarsGameStart(G game) {
        super(game);
        this.game = game;
        super.setMinimumPlayers(2);
        super.setCountdownMins(5);
        new Settings<>(game, world);
        new CageHandler<>(game);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (wallsGenerator.setupWallsGameArea(center, center) && !Bukkit.getOnlinePlayers().isEmpty()) {
                    startCountdown();
                    cancel();
                }
            }
        }.runTaskTimer(Util.keeleUniWars, 0, 20);
    }

    @Override
    protected void startCountdown() {
        super.startCountdown();
        new BukkitRunnable() {

            @Override
            public void run() {
                if (game.getGameStatus() == CoreGameStatus.IN_GAME) {
                    new WarsInGame<>(game, wallsGenerator);
                    cancel();
                }
            }
        }.runTaskTimer(Util.keeleUniWars, 0, 20);
    }
}