package dev.joeyfoxo.core.game;

import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CoreGame<G extends CoreGame<G>> {
    private static CoreGame instance = null;

    protected CoreGameStatus gameStatus = CoreGameStatus.WAITING;

    protected int maxPlayers = 16;

    protected Set<Team<G>> teamsList = new HashSet<>();
    public Set<TeamPlayer<G>> players = new HashSet<>();

    Team<G> redTeam = new Team<>(this, TeamColors.RED);
    Team<G> greenTeam = new Team<>(this, TeamColors.GREEN);
    Team<G> yellowTeam = new Team<>(this, TeamColors.YELLOW);
    Team<G> blueTeam = new Team<>(this, TeamColors.BLUE);

    public CoreGame() {
        populateTeams();
    }

    public Team<G> getTeam(TeamColors colors) {
        for (Team<G> team : teamsList) {
            if (team.getTeamColor() == colors) {
                return team;
            }
        }
        return null;
    }

    public Team<G> getTeam(TeamPlayer<G> player) {
        for (Team<G> team : teamsList) {
            for (TeamPlayer<?> teamPlayer : team.getTeamMembers()) {
                if (teamPlayer == player) {
                    return team;
                }
            }
        }
        return null;
    }

    protected void populateTeams() {
        teamsList.add(redTeam);
        teamsList.add(greenTeam);
        teamsList.add(yellowTeam);
        teamsList.add(blueTeam);

    }

    public Team<G> getTeamWithFewestMembers() {
        return Stream.of(redTeam, greenTeam, yellowTeam, blueTeam)
                .min(Comparator.comparing(team -> team.getTeamMembers().size()))
                .orElse(null);
    }

    public TeamPlayer<G> createTeamPlayer(G game, Team<G> team, Player player) {
        TeamPlayer<G> teamPlayer = new TeamPlayer<>(game, team.getTeamColor(), player);
        players.add(teamPlayer);
        return teamPlayer;
    }

    public static CoreGame getInstance() {
        if (instance == null) {
            instance = new CoreGame<>();
        }
        return instance;
    }

    public void setGameStatus(CoreGameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public CoreGameStatus getGameStatus() {
        return gameStatus;
    }

    public TeamPlayer<G> getPlayer(Player player) {
        for (TeamPlayer<G> teamPlayer : players) {
            if (teamPlayer.getPlayer() == player) {
                return teamPlayer;
            }
        }
        return null;
    }

    public int getAlivePlayers() {

        AtomicInteger alivePlayers = new AtomicInteger();

        players.forEach(player -> {

            if (!player.isSpectator()) {
                alivePlayers.getAndIncrement();
            }

        });
        return alivePlayers.get();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}

