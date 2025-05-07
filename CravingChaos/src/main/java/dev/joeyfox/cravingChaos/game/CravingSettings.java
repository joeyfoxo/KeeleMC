package dev.joeyfox.cravingChaos.game;

import dev.joeyfoxo.core.game.CoreSettings;

public class CravingSettings extends CoreSettings<CravingGame> {

    public CravingSettings(CravingGame game) {
        super(game);

        CoreSettings.maxPlayers = 2;
        CoreSettings.minPlayers = 1;

    }
}
