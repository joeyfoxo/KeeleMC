package dev.joeyfoxo.keeleuniwars.game.teams;

import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;

public class WallsTeam<G extends WallsGame<G>> extends Team<G> {

    WallsGame<G> wallsGame;
    TeamColors teamColors;

    public WallsTeam(WallsGame<G> wallsGame, TeamColors teamColor) {
        super(wallsGame, teamColor);
        this.wallsGame = wallsGame;
        this.teamColors = teamColor;
    }

    public void addPlayer(WallsPlayer<G> player) {
        super.players.add(player);
    }

    public void removePlayer(WallsPlayer<G> player) {
        players.remove(player);
    }

}
