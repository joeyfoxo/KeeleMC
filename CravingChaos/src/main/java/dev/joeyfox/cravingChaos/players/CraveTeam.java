package dev.joeyfox.cravingChaos.players;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;

public class CraveTeam extends Team<CravingGame> {

    public CraveTeam(CoreGame<CravingGame> coreGame, TeamColors teamColor) {
        super(coreGame, teamColor);
    }
}
