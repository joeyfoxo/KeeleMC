package dev.joeyfoxo.core.game.events;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public abstract class CoreCageHandler<G extends CoreGame<G>> implements Listener {

    G game;
    protected HashMap<Team<G>, HashMap<UUID, Location>> teamSpawnLocations = new HashMap<>();

    protected World world = Core.getKeeleMiniCore().getServer().getWorlds().get(0);

    public CoreCageHandler(G game) {
        Core.getKeeleMiniCore().getServer().getPluginManager().registerEvents(this, Core.getKeeleMiniCore());
        this.game = game;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        if (game.getGameStatus() == GameStatus.NOT_READY) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.empty());
            return;
        }
        if (game.getGameStatus() == GameStatus.IN_GAME  ||
        game.getGameStatus() == GameStatus.WALLS_UP || game.getAlivePlayers() >= game.getMaxPlayers()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, Component.text("Game is full!"));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team<G> team = game.getTeamWithFewestMembers();
        TeamPlayer<G> teamPlayer;
        if (team != null) {
            if (game.getPlayer(player) == null) {
                teamPlayer = game.createTeamPlayer(game, team, player);
                team.addPlayer(teamPlayer);
            } else {
                teamPlayer = game.getPlayer(player);
            }
            Location cageLocation = findNextAvailableCage(team, teamPlayer);
            player.teleport(cageLocation);
            teamSpawnLocations.computeIfAbsent(team, k -> new HashMap<>()).putIfAbsent(player.getUniqueId(), cageLocation);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (game.getGameStatus() == GameStatus.WAITING) {
            // Allow player to turn around but not to change location
            if (!event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (game.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Team<G> team = null;
        for (Team<G> t : teamSpawnLocations.keySet()) {
            if (teamSpawnLocations.get(t).containsKey(playerId)) {
                team = t;
                break;
            }
        }
        if (team != null) {
            TeamPlayer<G> teamPlayer = team.getTeamMembers().stream()
                    .filter(p -> p.getPlayer().getUniqueId().equals(playerId))
                    .findFirst()
                    .orElse(null);
            if (teamPlayer != null) {
                team.removePlayer(teamPlayer);
                Location cageLocation = teamSpawnLocations.get(team).remove(playerId);
                if (cageLocation != null) {
                    // Remove the cage location from the team's spawn locations
                    teamSpawnLocations.get(team).remove(playerId);
                    world.getBlockAt(cageLocation).setType(Material.AIR);
                }
            }
        }
    }

    public abstract Location findNextAvailableCage(Team<G> team, TeamPlayer<G> player);
}
