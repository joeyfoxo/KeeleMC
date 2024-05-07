package dev.joeyfoxo.keeleuniwars.game.events;

import dev.joeyfoxo.core.Core;
import dev.joeyfoxo.core.game.CoreGame;
import dev.joeyfoxo.core.game.teams.Team;
import dev.joeyfoxo.core.game.teams.TeamPlayer;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsPlayer;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GameHandler<G extends WallsGame<G>> implements Listener {

    G game;

    public GameHandler(G game) {
        Core.getKeeleMiniCore().getServer().getPluginManager().registerEvents(this, Core.getKeeleMiniCore());
        this.game = game;

    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player victim) || !(event.getDamager() instanceof Player attacker)) {
            return;
        }

        WallsPlayer<G> victimPlayer = game.getPlayer(victim);
        WallsPlayer<G> attackerPlayer = game.getPlayer(attacker);
        WallsTeam<G> victimTeam = game.getTeam(victimPlayer);
        WallsTeam<G> attackerTeam = game.getTeam(attackerPlayer);

        if (victimTeam.getTeamMembers().contains(attackerPlayer) || attackerTeam.getTeamMembers().contains(victimPlayer)) {
            event.setCancelled(true);
        }
    }

}
