package dev.joeyfox.cravingChaos.cage;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfox.cravingChaos.game.CravingSettings;
import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;

public class CravingCageHandler extends CoreCageHandler<CravingGame> {

    private final CravingGame game;

    public CravingCageHandler(CravingGame game) {
        super(game);
        this.game = game;
    }


    @Override
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team<CravingGame> team = game.getTeamWithFewestMembers();

        if (team == null) {
            player.kick(Component.text("Unable to join game: no teams available."));
            return;
        }

        TeamPlayer<CravingGame> teamPlayer = game.getPlayer(player);
        if (teamPlayer == null) {
            teamPlayer = game.createTeamPlayer(team, player);
        }

        team.addPlayer(teamPlayer);

        Location cageLocation = findNextAvailableCage(team, teamPlayer);
        teamSpawnLocations.computeIfAbsent(team, k -> new HashMap<>()).put(player.getUniqueId(), cageLocation);
        player.teleport(cageLocation);
    }

    @Override
    public Location findNextAvailableCage(Team<CravingGame> team, TeamPlayer<CravingGame> player) {
        World world = Bukkit.getWorld("glass");

        // Find the top-most non-air block starting from a safe max height
        int y = 200;
        while (y > 0 && world.getBlockAt(0, y, 0).getType() == Material.AIR || y > 0 && world.getBlockAt(0, y, 0).getType() == Material.BARRIER) {
            y--;
        }

        // Base teleport height just above the highest non-air block
        Location base = new Location(world, 0, y + 1, 0);
        List<TeamPlayer<CravingGame>> playersInTeam = team.getTeamMembers().stream().toList();
        int index = playersInTeam.indexOf(player);

        // Put players on opposite sides along the X-axis
        if (index == 0) {
            base = base.clone().add((double) -CravingSettings.getCageSize() / 4, 0, 0); // West side
            player.setSpawnLocation(base);
            return base;
        } else if (index == 1) {
            base = base.clone().add((double) CravingSettings.getCageSize() / 4, 0, 0); // West side
            player.setSpawnLocation(base);
            return base;
        }

        return base; // Fallback
    }
}
