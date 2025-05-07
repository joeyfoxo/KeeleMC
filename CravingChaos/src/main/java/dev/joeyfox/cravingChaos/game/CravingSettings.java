package dev.joeyfox.cravingChaos.game;

import dev.joeyfoxo.core.game.CoreSettings;

public class CravingSettings extends CoreSettings<CravingGame> {

    private static int size = 25; // Default size of the glass box
    private static int height = 25; // Default size of the glass box

    public CravingSettings(CravingGame game) {
        super(game);

        CoreSettings.maxPlayers = 2;
        CoreSettings.minPlayers = 1;

    }

    public static int getCageSize() {
        return size;
    }

    public static int getCageHeight() {
        return height;
    }
}
