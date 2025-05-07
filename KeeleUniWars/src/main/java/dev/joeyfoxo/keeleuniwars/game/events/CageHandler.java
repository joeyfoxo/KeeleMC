package dev.joeyfoxo.keeleuniwars.game.events;

import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.generator.WallsGenerator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static dev.joeyfoxo.keeleuniwars.game.Settings.wallSize;

public class CageHandler extends CoreCageHandler<WallsGame> implements Listener {

  public CageHandler(WallsGame game) {
    super(game);
  }

  @Override
  public Location findNextAvailableCage(Team<WallsGame> team, TeamPlayer<WallsGame> player) {
    Map<UUID, Location> currentTeamSpawns = teamSpawnLocations.computeIfAbsent(team, k -> new HashMap<>());

    int offset = currentTeamSpawns.size();
    int radius = wallSize / 2; // Place players around the border of the arena
    World world = player.getPlayer().getWorld();

    // Assign a base angle per team
    double baseAngle;
    switch (player.getTeamColor()) {
      case RED -> baseAngle = 0;
      case GREEN -> baseAngle = 90;
      case YELLOW -> baseAngle = 180;
      case BLUE -> baseAngle = 270;
      default -> throw new IllegalStateException("Unexpected value: " + player.getTeamColor());
    }

    // Spread players a few degrees apart to prevent overlapping
    double angle = Math.toRadians(baseAngle + offset * 10); // 10 degrees spacing
    int x = WallsGenerator.center + (int) (radius * Math.cos(angle));
    int z = WallsGenerator.center + (int) (radius * Math.sin(angle));
    int y = world.getHighestBlockYAt(x, z) + 1;

    Location cageLocation = new Location(world, x + 0.5, y, z + 0.5);

    Material wool = player.getTeamColor().getWoolMaterial();
    if (wool != null) {
      world.getBlockAt(cageLocation).setType(wool);
    }

    cageLocation.add(new Vector(0, 1, 0)); // Stand one block above the wool
    currentTeamSpawns.put(player.getPlayer().getUniqueId(), cageLocation);

    return cageLocation;
  }
}