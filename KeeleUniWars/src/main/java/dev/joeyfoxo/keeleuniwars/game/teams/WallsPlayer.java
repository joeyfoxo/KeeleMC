package dev.joeyfoxo.keeleuniwars.game.teams;

import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import org.bukkit.entity.Player;

public class WallsPlayer extends TeamPlayer<WallsGame> {

    public WallsPlayer(WallsGame game, TeamColors teamColor, Player player) {
        super(game, teamColor, player);
    }
}