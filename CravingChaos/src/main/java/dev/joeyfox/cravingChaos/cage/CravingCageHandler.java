package dev.joeyfox.cravingChaos.cage;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class CravingCageHandler extends CoreCageHandler<CravingGame> {

    public CravingCageHandler(CravingGame game) {
        super(game);
    }

    @Override
    public Location findNextAvailableCage(Team<CravingGame> team, TeamPlayer<CravingGame> player) {
        Location base = new Location(Bukkit.getWorld("glass"), 0, 105, 0);
        List<TeamPlayer<CravingGame>> playersInTeam = team.getTeamMembers().stream().toList();
        int index = playersInTeam.indexOf(player);

        if (index == 0) {
            return base.clone().add(-1.5, 1, 0);
        } else if (index == 1) {
            return base.clone().add(1.5, 1, 0);
        }

        return base.clone().add(0, 1, 0);
    }
}
