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

public class WallsGame extends CoreGame<WallsGame> {

    public static WallsGame instance;
    WallsTeam redTeam = new WallsTeam(this, TeamColors.RED);
    WallsTeam greenTeam = new WallsTeam(this, TeamColors.GREEN);
    WallsTeam yellowTeam = new WallsTeam(this, TeamColors.YELLOW);
    WallsTeam blueTeam = new WallsTeam(this, TeamColors.BLUE);

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
    public WallsPlayer getPlayer(Player player) {
        for (TeamPlayer<? extends CoreGame<WallsGame>> wallsPlayer : players) {
            if (wallsPlayer.getPlayer() == player) {
                if (wallsPlayer instanceof WallsPlayer wallPlayer) {
                    return wallPlayer;
                }
            }
        }
        return null;
    }

    @Override
    public WallsTeam getTeam(TeamPlayer<WallsGame> player) {
        for (Team<WallsGame> team : teamsList) {
            if (team instanceof WallsTeam wallsTeam) {
                if (wallsTeam.getTeamMembers().contains(player)) {
                    return wallsTeam;
                }
            }
        }

        return null;
    }

    @Override
    public WallsTeam getTeamWithFewestMembers() {
        return Stream.of(redTeam, greenTeam, yellowTeam, blueTeam)
                .min(Comparator.comparingInt(team -> team.getTeamMembers().size()))
                .orElse(null);
    }


    @Override
    public WallsPlayer createTeamPlayer(Team<WallsGame> team, Player player) {
        WallsPlayer teamPlayer = new WallsPlayer(this, team.getTeamColor(), player);
        players.add(teamPlayer);
        return teamPlayer;
    }

    public static WallsGame getInstance() {
        if (instance == null) {
            instance = new WallsGame();
        }
        return instance;
    }


}