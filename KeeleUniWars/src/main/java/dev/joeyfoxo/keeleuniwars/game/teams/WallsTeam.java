package dev.joeyfoxo.keeleuniwars.game.teams;

import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;

public class WallsTeam extends Team<WallsGame> {

    public WallsTeam(WallsGame game, TeamColors teamColor) {
        super(game, teamColor);
    }

    public void addPlayer(WallsPlayer player) {
        getTeamMembers().add(player); // Use getter from base class
    }

    public void removePlayer(WallsPlayer player) {
        getTeamMembers().remove(player); // Use getter from base class
    }
}