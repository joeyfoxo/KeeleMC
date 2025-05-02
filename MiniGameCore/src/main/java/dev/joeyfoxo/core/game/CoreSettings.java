package dev.joeyfoxo.core.game;

public class CoreSettings<G extends CoreGame<G>> {

    public CoreSettings(G game) {
    }

    public static int minPlayers = 2;
    public static int maxPlayers = 16;
    public static int countdownMins = 1;

}
