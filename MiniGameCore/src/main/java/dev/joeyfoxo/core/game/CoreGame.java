package dev.joeyfoxo.core.game;

import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoreGame<G extends CoreGame<G>> {
    private static CoreGame instance = null;

    protected GameStatus gameStatus = GameStatus.NOT_READY;

    protected int maxPlayers = CoreSettings.maxPlayers;

    protected Set<Team<G>> teamsList = new HashSet<>();
    public Set<TeamPlayer<G>> players = new HashSet<>();
    boolean isTeammed;

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

    public void setTeamed(boolean teamed) {
        isTeammed = teamed;
    }

    public boolean isTeamed() {
        return isTeammed;
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

    public void removePlayer(TeamPlayer<G> player) {
        players.remove(player);
    }

    public static CoreGame getInstance() {
        if (instance == null) {
            instance = new CoreGame<>();
        }
        return instance;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
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

    public int getAlivePlayerCount() {

        AtomicInteger alivePlayers = new AtomicInteger();

        players.forEach(player -> {

            if (!player.isSpectator()) {
                alivePlayers.getAndIncrement();
            }

        });
        return alivePlayers.get();
    }

    public List<TeamPlayer<G>> getAlivePlayers() {
        return players.stream().filter(player -> !player.isSpectator()).toList();
    }
    public Set<Team<G>> getTeamsAlive() {
        return teamsList.stream()
                .filter(Objects::nonNull) // Ensure the team is not null
                .filter(team -> team.getTeamMembers() != null) // Ensure team members are not null
                .filter(team -> team.getTeamMembers().stream().anyMatch(player -> player != null && !player.isSpectator()))
                .collect(Collectors.toSet());
    }



    public int getMaxPlayers() {
        return maxPlayers;
    }
}

