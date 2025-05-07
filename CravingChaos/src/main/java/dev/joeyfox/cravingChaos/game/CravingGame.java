package dev.joeyfox.cravingChaos.game;

import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.stream.Stream;

public class CravingGame extends CoreGame<CravingGame> {

    private static CravingGame instance;

    private final Team<CravingGame> redTeam = new Team<>(this, TeamColors.RED);
    private final Team<CravingGame> blueTeam = new Team<>(this, TeamColors.BLUE);

    public CravingGame() {
        populateTeams();
    }

    @Override
    protected void populateTeams() {
        teamsList.add(redTeam);
        teamsList.add(blueTeam);
    }

    @Override
    public TeamPlayer<CravingGame> getPlayer(Player player) {
        return players.stream()
                .filter(p -> p.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Team<CravingGame> getTeam(TeamPlayer<CravingGame> player) {
        return teamsList.stream()
                .filter(team -> team.getTeamMembers().contains(player))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Team<CravingGame> getTeamWithFewestMembers() {
        return Stream.of(redTeam, blueTeam)
                .min(Comparator.comparingInt(team -> team.getTeamMembers().size()))
                .orElse(null);
    }

    @Override
    public TeamPlayer<CravingGame> createTeamPlayer(Team<CravingGame> team, Player player) {
        TeamPlayer<CravingGame> teamPlayer = new TeamPlayer<>(this, team.getTeamColor(), player);
        players.add(teamPlayer);
        return teamPlayer;
    }

    public static CravingGame getInstance() {
        if (instance == null) {
            instance = new CravingGame();
        }
        return instance;
    }
}