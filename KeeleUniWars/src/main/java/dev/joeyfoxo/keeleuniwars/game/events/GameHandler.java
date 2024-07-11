package dev.joeyfoxo.keeleuniwars.game.events;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsPlayer;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsTeam;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameHandler<G extends WallsGame<G>> implements Listener {

    G game;

    public GameHandler(G game) {
        Core.getKeeleMiniCore().getServer().getPluginManager().registerEvents(this, Core.getKeeleMiniCore());
        this.game = game;
    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageByEntityEvent event) {

        if (game.getGameStatus() != GameStatus.IN_GAME) {
            event.setCancelled(true);
            return;
        }

        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {

            WallsPlayer<G> victimPlayer = game.getPlayer(victim);
            WallsPlayer<G> attackerPlayer = game.getPlayer(attacker);
            WallsTeam<G> victimTeam = game.getTeam(victimPlayer);
            WallsTeam<G> attackerTeam = game.getTeam(attackerPlayer);

            if (victimTeam.getTeamMembers().contains(attackerPlayer) || attackerTeam.getTeamMembers().contains(victimPlayer)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        if (game.getGameStatus() == GameStatus.WALLS_UP || game.getGameStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        WallsPlayer<? extends WallsGame<G>> wallsPlayer = game.getPlayer(player);
        wallsPlayer.setSpectator(true);

        if (wallsPlayer.isSpectator()) {
            player.setGameMode(GameMode.SPECTATOR);
        }

    }

}