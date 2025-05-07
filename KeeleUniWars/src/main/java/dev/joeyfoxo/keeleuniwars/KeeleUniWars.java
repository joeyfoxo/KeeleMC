package dev.joeyfoxo.keeleuniwars;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.WarsGameStart;

public class KeeleUniWars extends Core<WallsGame> {

    private WallsGame game;

    @Override
    public void onEnable() {
        super.onEnable(); // This will call createGameInstance() inside Core
        new WarsGameStart(game); // Initialize game logic after it's created
    }

    @Override
    public void onDisable() {
        if (game != null) {
            game.shutdown();
        }
    }

    @Override
    protected WallsGame createGameInstance() {
        this.game = new WallsGame();
        return game;
    }

    public WallsGame getGame() {
        return game;
    }
}