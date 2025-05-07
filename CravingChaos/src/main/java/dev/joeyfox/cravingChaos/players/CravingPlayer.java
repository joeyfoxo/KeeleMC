package dev.joeyfox.cravingChaos.players;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.entity.Player;

public class CravingPlayer extends TeamPlayer<CravingGame> {

    public CravingPlayer(CoreGame<CravingGame> game, TeamColors teamColor, Player player) {
        super(game, teamColor, player);
    }
}
