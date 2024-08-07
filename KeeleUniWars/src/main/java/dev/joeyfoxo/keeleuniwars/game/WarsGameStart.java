package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.keeleuniwars.game.events.CageHandler;
import dev.joeyfoxo.keeleuniwars.generator.WallsGenerator;
import dev.joeyfoxo.keeleuniwars.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

import static dev.joeyfoxo.keeleuniwars.generator.WallsGenerator.center;

public class WarsGameStart<G extends WallsGame<G>> extends CoreGameStart<G> {
    G game;

    WallsGenerator wallsGenerator = new WallsGenerator();
    boolean ready = false;

    public WarsGameStart(G game) {
        super(game);
        this.game = game;
//        super.setMinimumPlayers(CoreSettings.minPlayers); -- Can edit if needs
//        super.setCountdownMins(CoreSettings.countdownMins); -- Can edit if needs
        new Settings<>(game, world);
        new CageHandler<>(game);
        new BukkitRunnable() {

            @Override
            public void run() {

                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    startCountdown();
                    cancel();
                    return;
                }

                if (!ready && wallsGenerator.setupWallsGameArea(center, center, game)) {
                    ready = true;
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

                if (countdownSeconds <= 0) {
                    new WarsInGame<>(game, world);
                    game.setGameStatus(GameStatus.WALLS_UP);
                    cancel();
                }
            }
        }.runTaskTimer(Util.keeleUniWars, 0, 20);
    }
}