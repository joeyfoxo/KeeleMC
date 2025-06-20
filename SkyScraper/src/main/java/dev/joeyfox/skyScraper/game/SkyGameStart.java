package dev.joeyfox.skyScraper.game;

import dev.joeyfox.skyScraper.SkyScraper;
import dev.joeyfox.skyScraper.world.SkyWorldGenerator;
import dev.joeyfoxo.core.game.CoreGameStart;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class SkyGameStart extends CoreGameStart<SkyGame> {
    /**
     * Constructor for the CoreGameStart class.
     * Initializes the game instance and creates a new CoreSettings instance for the world.
     *
     * @param game The game instance this class will manage.
     */
    public SkyGameStart(SkyGame game) {
        super(game);
        new SkyWorldGenerator();
    }
}
