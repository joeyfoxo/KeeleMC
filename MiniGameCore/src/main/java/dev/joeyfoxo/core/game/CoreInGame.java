package dev.joeyfoxo.core.game;

public class CoreInGame<G extends CoreGame<G>> {

    G game;

    public CoreInGame(G game) {
        this.game = game;
    }

}
