package dev.joeyfoxo.keeleuniwars.game.teams;

import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import org.bukkit.entity.Player;

public class WallsPlayer<G extends WallsGame<G>> extends TeamPlayer<G> {

    public WallsPlayer(WallsGame<G> game, TeamColors teamColor, Player player) {
        super(game, teamColor, player);
    }
}
