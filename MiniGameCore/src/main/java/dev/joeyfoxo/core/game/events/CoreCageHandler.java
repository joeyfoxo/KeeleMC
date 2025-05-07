package dev.joeyfoxo.core.game.events;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

import java.util.*;

public abstract class CoreCageHandler<G extends CoreGame<G>> implements Listener {

    protected final G game;
    protected final Map<Team<G>, Map<UUID, Location>> teamSpawnLocations = new HashMap<>();

    public CoreCageHandler(G game) {
        this.game = game;
        Bukkit.getPluginManager().registerEvents(this, Core.getKeeleMiniCore());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {

        //TODO: TEMP - remove after
//        GameStatus status = game.getGameStatus();
//        if (status == GameStatus.NOT_READY) {
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
//                    Component.text("Game is not ready."));
//        } else if (status == GameStatus.IN_GAME || status == GameStatus.WALLS_UP) {
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
//                    Component.text("Game is in progress!"));
//        } else if (game.getAlivePlayerCount() >= game.getMaxPlayers()) {
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL,
//                    Component.text("Game is full!"));
//        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team<G> team = game.getTeamWithFewestMembers();

        if (team == null) {
            player.kick(Component.text("Unable to join game: no teams available."));
            return;
        }

        TeamPlayer<G> teamPlayer = game.getPlayer(player);
        if (teamPlayer == null) {
            teamPlayer = game.createTeamPlayer(team, player);
        }

        team.addPlayer(teamPlayer);

        Location cageLocation = findNextAvailableCage(team, teamPlayer);
        teamSpawnLocations.computeIfAbsent(team, k -> new HashMap<>()).put(player.getUniqueId(), cageLocation);
        player.teleport(cageLocation);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (game.getGameStatus() == GameStatus.WAITING &&
                !event.getFrom().getBlock().equals(event.getTo().getBlock())) {
            event.setCancelled(true);
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
        UUID playerId = event.getPlayer().getUniqueId();
        World world = event.getPlayer().getWorld();

        teamSpawnLocations.forEach((team, spawns) -> {
            if (spawns.containsKey(playerId)) {
                Location cageLocation = spawns.remove(playerId).subtract(0, 1, 0);
                world.getBlockAt(cageLocation).setType(Material.AIR);

                team.getTeamMembers().removeIf(p -> p.getPlayer().getUniqueId().equals(playerId));
                game.getAlivePlayers().removeIf(p -> p.getPlayer().getUniqueId().equals(playerId));
            }
        });
    }

    /**
     * Define how cage spawn locations are allocated for each team/player.
     */
    public abstract Location findNextAvailableCage(Team<G> team, TeamPlayer<G> player);
}