package dev.joeyfoxo.core.game.teams;

import dev.joeyfoxo.core.game.CoreGame;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class Team<G extends CoreGame<G>> {

    protected HashSet<TeamPlayer<G>> players = new HashSet<>();

    TeamColors teamColors;
    CoreGame<G> coreGame;

    public Team(CoreGame<G> coreGame, TeamColors teamColor) {

        this.teamColors = teamColor;
        this.coreGame = coreGame;
    }

    public void addPlayer(TeamPlayer<G> player) {
        players.add(player);
    }
    public void removePlayer(TeamPlayer<G> player) {
        players.remove(player);
    }

    public HashSet<TeamPlayer<G>> getTeamMembers() {
        return players;
    }

    public TeamColors getTeamColor() {
        return teamColors;
    }


}
