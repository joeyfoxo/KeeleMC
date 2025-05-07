package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreSettings;
import dev.joeyfoxo.keeleuniwars.game.events.GameHandler;
import dev.joeyfoxo.keeleuniwars.game.gamerule.GameRules;
import org.bukkit.GameMode;
import org.bukkit.World;

public class Settings extends CoreSettings<WallsGame> {

    WallsGame game;
    public static GameMode defaultGameMode = GameMode.SURVIVAL;
    public static boolean debugMode = false;
    public static int wallCountDownMinutes = 1;
    public static int wallSize = 200;

    public Settings(WallsGame game, World world) {
        super(game);
        this.game = game;
        new GameRules(game, world);
        new GameHandler(game);
        game.setTeamed(true);
        CoreSettings.countdownMins = wallCountDownMinutes; //TODO: Make this into the game core and therefore can be changed without static

    }

}
