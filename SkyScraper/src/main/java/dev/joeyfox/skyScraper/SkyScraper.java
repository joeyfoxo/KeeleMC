package dev.joeyfox.skyScraper;

import dev.joeyfox.skyScraper.game.SkyGame;
import dev.joeyfoxo.core.Core;

public final class SkyScraper extends Core<SkyGame> {

    private static SkyGame game;
    @Override
    public void onEnable() {
       super.onEnable();

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    protected SkyGame createGameInstance() {
        if (game == null) {
            game = new SkyGame();
        }
        return game;
    }
}
