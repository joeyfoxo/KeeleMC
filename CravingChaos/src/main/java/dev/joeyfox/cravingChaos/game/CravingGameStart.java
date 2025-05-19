package dev.joeyfox.cravingChaos.game;

import dev.joeyfox.cravingChaos.CravingChaos;
import dev.joeyfox.cravingChaos.cage.CravingCageHandler;
import dev.joeyfox.cravingChaos.world.WorldGenerator;
import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CravingGameStart extends CoreGameStart<CravingGame> {

    CravingGame game;

    public CravingGameStart(CravingGame game) {
        super(game);
        this.game = game;
        CravingSettings settings = new CravingSettings(game);
        CravingCageHandler cravingCageHandler = new CravingCageHandler(game);
        game.setTeamed(false); // Disable team logic

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    startCountdown();
                    cancel();
                }
            }
        }.runTaskTimer(CravingChaos.getKeeleMiniCore(), 0L, 20L);
    }

    @Override
    protected void startCountdown() {
        super.startCountdown();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (countdownSeconds <= 0) {
                    new CravingInGame(game);
                    cancel();
                }
            }
        }.runTaskTimer(CravingChaos.getKeeleMiniCore(), 0L, 20L);
    }
}