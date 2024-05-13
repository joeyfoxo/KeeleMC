package dev.joeyfoxo.keeleuniwars.game.events;

import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.events.CoreCageHandler;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.KeeleUniWars;
import dev.joeyfoxo.keeleuniwars.game.Settings;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsPlayer;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsTeam;
import dev.joeyfoxo.keeleuniwars.generator.WallsGenerator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class CageHandler<G extends WallsGame<G>> extends CoreCageHandler<G> implements Listener {

  G game;

  public CageHandler(G game) {
    super(game);
    this.game = game;
    KeeleUniWars.getKeeleMiniCore().getServer().getPluginManager()
            .registerEvents(this, KeeleUniWars.getKeeleMiniCore());
  }

  @Override
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    WallsTeam<G> team = game.getTeamWithFewestMembers();
    for (TeamPlayer<? extends CoreGame<G>> wallsPlayer : game.players) {
      if (wallsPlayer.getPlayer() == player) {
        return;
      }
    }
    if (team != null) {
      WallsPlayer<G> teamPlayer = game.createTeamPlayer(game, team, player);
      team.addPlayer(teamPlayer);
    }

    player.setGameMode(Settings.defaultGameMode);

    super.onPlayerJoin(event);
  }


  @Override
  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    TeamPlayer<G> teamPlayer = game.getPlayer(player);
    if (teamPlayer == null) {
      return;
    }
    Team<G> team = game.getTeam(teamPlayer);

    if (team == null) {
      return;
    }

    team.removePlayer(teamPlayer);
    super.onLeave(event);
  }

  public Location findNextAvailableCage(Team<G> team, TeamPlayer<G> player) {
    HashMap<UUID, Location> currentTeamSpawns = teamSpawnLocations.computeIfAbsent(team, k -> new HashMap<>());
    int x, z;
    int wallBlock = 1; // Assuming the wall block is 1 unit wide
    int offset = currentTeamSpawns.size(); // Offset based on the number of players already in the team

    switch (player.getTeamColor()) {
      case RED -> {
        x = WallsGenerator.center - WallsGenerator.wallSize / 4 - wallBlock + offset;
        z = WallsGenerator.center - WallsGenerator.wallSize / 4 - wallBlock;
      }
      case GREEN -> {
        x = WallsGenerator.center + WallsGenerator.wallSize / 4 + wallBlock - offset;
        z = WallsGenerator.center - WallsGenerator.wallSize / 4 - wallBlock;
      }
      case YELLOW -> {
        x = WallsGenerator.center - WallsGenerator.wallSize / 4 - wallBlock + offset;
        z = WallsGenerator.center + WallsGenerator.wallSize / 4 + wallBlock;
      }
      case BLUE -> {
        x = WallsGenerator.center + WallsGenerator.wallSize / 4 + wallBlock - offset;
        z = WallsGenerator.center + WallsGenerator.wallSize / 4 + wallBlock;
      }
      default -> throw new IllegalStateException("Unexpected value: " + player.getTeamColor());
    }

    int y = world.getHighestBlockYAt(x, z) + 1;
    Location newLocation = new Location(world, x + 0.5, y, z + 0.5);
    world.getBlockAt(newLocation).setType(player.getTeamColor().getWoolMaterial());
    newLocation.add(new Vector(0, 1, 0));
    currentTeamSpawns.put(player.getPlayer().getUniqueId(), newLocation); // Add the new location to the team's spawn locations
    return newLocation;
  }
}