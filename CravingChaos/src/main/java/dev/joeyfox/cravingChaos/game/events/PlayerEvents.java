package dev.joeyfox.cravingChaos.game.events;

import dev.joeyfox.cravingChaos.CravingChaos;
import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerEvents implements Listener {

    CravingGame game;

    public PlayerEvents(CravingGame game) {
        this.game = game;
        Bukkit.getPluginManager().registerEvents(this, CravingChaos.getKeeleMiniCore());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        TeamPlayer<CravingGame> teamPlayer = game.getPlayer(player);
        if (game.getGameStatus() != GameStatus.IN_GAME) {
            player.teleport(teamPlayer.getSpawnLocation());
            event.setCancelled(true);
        }

    }

}
