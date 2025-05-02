package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreSettings;
import dev.joeyfoxo.keeleuniwars.game.events.GameHandler;
import dev.joeyfoxo.keeleuniwars.game.gamerule.GameRules;
import org.bukkit.GameMode;
import org.bukkit.World;

public class Settings<G extends WallsGame<G>> extends CoreSettings<G> {

    G game;
    public static GameMode defaultGameMode = GameMode.SURVIVAL;
    public static boolean debugMode = false;
    public static int wallCountDownMinutes = 1;
    public static int wallSize = 200;

    public Settings(G game, World world) {
        super(game);
        this.game = game;
        new GameRules<>(game, world);
        new GameHandler<>(game);
        game.setTeamed(true);
        CoreSettings.countdownMins = 1; //TODO: Make this into the game core and therefore can be changed without static

    }

}
