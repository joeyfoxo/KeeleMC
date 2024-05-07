package dev.joeyfoxo.keeleuniwars;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.WarsGameStart;

public class KeeleUniWars<G extends WallsGame<G>> extends Core<G> {

    @Override
    public void onEnable() {
        super.onEnable();
        G game = (G) WallsGame.getInstance();
        new WarsGameStart<>(game);

    }

}
