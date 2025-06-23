package dev.joeyfox.skyScraper.game;

import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SkyCageHandler extends CoreCageHandler<SkyGame> {
    public SkyCageHandler(SkyGame game) {
        super(game);
    }

    @Override
    public Location findNextAvailableCage(Team<SkyGame> team, TeamPlayer<SkyGame> player) {

        return new Location(Bukkit.getWorlds().get(0), 0,0 ,0);
    }
}
