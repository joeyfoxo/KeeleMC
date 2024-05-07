package dev.joeyfoxo.core.game.teams;

import dev.joeyfoxo.core.game.CoreGame;
import org.bukkit.entity.Player;

public class TeamPlayer<G extends CoreGame<G>> {

    private TeamColors teamColor;
    Player player;
    boolean isSpectator = false;
    CoreGame<G> game;

    public TeamPlayer(CoreGame<G> game, TeamColors teamColor, Player player) {
        this.teamColor = teamColor;
        this.player = player;
        this.game =game;
    }

    public TeamColors getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(TeamColors teamColor) {
        this.teamColor = teamColor;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }
}
