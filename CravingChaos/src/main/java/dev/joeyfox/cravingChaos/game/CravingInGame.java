package dev.joeyfox.cravingChaos.game;

import dev.joeyfoxo.core.game.CoreInGame;
import dev.joeyfoxo.core.game.GameStatus;

public class CravingInGame extends CoreInGame<CravingGame> {


    public CravingInGame(CravingGame game) {
        super(game);
        game.setGameStatus(GameStatus.IN_GAME);
    }
}
