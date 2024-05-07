package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreSettings;
import dev.joeyfoxo.keeleuniwars.game.events.GameHandler;
import dev.joeyfoxo.keeleuniwars.game.gamerule.GameRules;
import org.bukkit.GameMode;
import org.bukkit.World;

public class Settings<G extends WallsGame<G>> extends CoreSettings<G> {

    G game;
    public static GameMode defaultGameMode = GameMode.SURVIVAL;

    public Settings(G game, World world) {
        super(game);
        this.game = game;
        new GameRules<>(game, world);
        new GameHandler<>(game);

    }

}
