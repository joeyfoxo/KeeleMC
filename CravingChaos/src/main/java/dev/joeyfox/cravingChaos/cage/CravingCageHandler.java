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

        // Find the top-most solid block starting from a safe max height
        int y = 200;
        while (y > 0 && (
                world.getBlockAt(0, y, 0).getType() == Material.AIR ||
                        world.getBlockAt(0, y, 0).getType() == Material.BARRIER
        )) {
            y--;
        }

        Location base = new Location(world, 0, y + 1, 0);
        List<TeamPlayer<CravingGame>> playersInTeam = team.getTeamMembers().stream().toList();
        int index = playersInTeam.indexOf(player);

        double offset = CravingSettings.getCageSize() / 4.0;
        Location spawn;

        if (index == 0) {
            spawn = base.clone().add(-offset, 0, 0); // West
        } else if (index == 1) {
            spawn = base.clone().add(offset, 0, 0); // East
        } else {
            spawn = base.clone(); // fallback
        }

        // Place wool block of team color below the player
        Location woolLocation = spawn.clone().subtract(0, 1, 0);
        Material wool = team.getTeamColor().getWoolMaterial();
        world.getBlockAt(woolLocation).setType(wool);

        player.setSpawnLocation(spawn);
        return spawn;
    }
}
