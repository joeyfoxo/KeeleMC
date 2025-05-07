package dev.joeyfox.cravingChaos;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfox.cravingChaos.game.CravingGameStart;
import dev.joeyfoxo.core.Core;

public final class CravingChaos extends Core<CravingGame> {

    @Override
    public void onEnable() {
        super.onEnable(); // This will call createGameInstance() inside Core
        setKeeleMiniCore(this);
        new CravingGameStart(getGame());
        System.out.println("CravingChaos has been enabled!");
    }

    @Override
    protected CravingGame createGameInstance() {
        return new CravingGame();
    }
}
