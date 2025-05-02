package dev.joeyfoxo.keeleuniwars.game;

import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamColors;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsPlayer;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsTeam;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.stream.Stream;

public class WallsGame<G extends WallsGame<G>> extends CoreGame<G> {

    public static WallsGame instance;
    WallsTeam<G> redTeam = new WallsTeam<>(this, TeamColors.RED);
    WallsTeam<G> greenTeam = new WallsTeam<>(this, TeamColors.GREEN);
    WallsTeam<G> yellowTeam = new WallsTeam<>(this, TeamColors.YELLOW);
    WallsTeam<G> blueTeam = new WallsTeam<>(this, TeamColors.BLUE);

    public WallsGame() {
        populateTeams();
    }

    @Override
    protected void populateTeams() {
        teamsList.add(redTeam);
        teamsList.add(greenTeam);
        teamsList.add(yellowTeam);
        teamsList.add(blueTeam);
    }

    @Override
    public WallsPlayer<G> getPlayer(Player player) {
        for (TeamPlayer<? extends CoreGame<G>> wallsPlayer : players) {
            if (wallsPlayer.getPlayer() == player) {
                if (wallsPlayer instanceof WallsPlayer) {
                    return (WallsPlayer<G>) wallsPlayer;
                }
            }
        }
        return null;
    }

    @Override
    public WallsTeam<G> getTeam(TeamPlayer<G> player) {
        for (Team<G> team : teamsList) {
            if (team instanceof WallsTeam<G>) {
                WallsTeam<G> wallsTeam = (WallsTeam<G>) team;
                if (wallsTeam.getTeamMembers().contains(player)) {
                    return wallsTeam;
                }
            }
        }

        return null;
    }

    @Override
    public WallsTeam<G> getTeamWithFewestMembers() {
        return Stream.of(redTeam, greenTeam, yellowTeam, blueTeam)
                .min(Comparator.comparingInt(team -> team.getTeamMembers().size()))
                .orElse(null);
    }


    @Override
    public WallsPlayer<G> createTeamPlayer(G game, Team<G> team, Player player) {
        WallsPlayer<G> teamPlayer = new WallsPlayer<>(game, team.getTeamColor(), player);
        players.add(teamPlayer);
        return teamPlayer;
    }

    public static WallsGame getInstance() {
        if (instance == null) {
            instance = new WallsGame<>();
        }
        return instance;
    }


}