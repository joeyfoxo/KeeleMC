package dev.joeyfoxo.core.game;

import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
public class CoreGame<G extends CoreGame<G>> {

    protected GameStatus gameStatus = GameStatus.NOT_READY;
    protected int maxPlayers = CoreSettings.maxPlayers;
    protected Set<Team<G>> teamsList = new HashSet<>();
    protected Set<TeamPlayer<G>> players = new HashSet<>();

    private boolean isTeamed;

    public CoreGame() {
        populateTeams();
    }

    protected void populateTeams() {
        teamsList.add(new Team<>(this, TeamColors.RED));
        teamsList.add(new Team<>( this, TeamColors.GREEN));
        teamsList.add(new Team<>( this, TeamColors.YELLOW));
        teamsList.add(new Team<>(this, TeamColors.BLUE));
    }

    public TeamPlayer<G> createTeamPlayer(Team<G> team, Player player) {
        TeamPlayer<G> playerObj = new TeamPlayer<>(this, team.getTeamColor(), player);
        players.add(playerObj);
        return playerObj;
    }

    public void setTeamed(boolean isTeamed) {
        this.isTeamed = isTeamed;
    }

    public boolean isTeamed() {
        return isTeamed;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus status) {
        this.gameStatus = status;
    }

    public List<TeamPlayer<G>> getAlivePlayers() {
        return players.stream().filter(p -> !p.isSpectator()).toList();
    }

    public int getAlivePlayerCount() {
        return (int) players.stream().filter(p -> !p.isSpectator()).count();
    }

    public Set<Team<G>> getTeamsAlive() {
        return teamsList.stream()
                .filter(team -> team.getTeamMembers().stream().anyMatch(p -> !p.isSpectator()))
                .collect(Collectors.toSet());
    }

    public Team<G> getTeam(TeamColors color) {
        return teamsList.stream()
                .filter(t -> t.getTeamColor() == color)
                .findFirst().orElse(null);
    }

    public TeamPlayer<G> getPlayer(Player player) {
        return players.stream()
                .filter(p -> p.getPlayer().equals(player))
                .findFirst().orElse(null);
    }

    public Team<G> getTeam(TeamPlayer<G> player) {
        return teamsList.stream()
                .filter(t -> t.getTeamMembers().contains(player))
                .findFirst().orElse(null);
    }

    public Team<G> getTeamWithFewestMembers() {
        return teamsList.stream()
                .min(Comparator.comparingInt(t -> t.getTeamMembers().size()))
                .orElse(null);
    }

    public void removePlayer(TeamPlayer<G> player) {
        players.remove(player);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void shutdown() {
        gameStatus = GameStatus.FINISHED;
    }
}