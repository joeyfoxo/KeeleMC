package dev.joey.keelesurvival.server.economy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.Util.keeleSurvival;


public class PlayerEconomy implements Listener {

    public PlayerEconomy() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore() || !getEconomy().hasAccount(player)) {
            getEconomy().createPlayerAccount(player);
            getEconomy().depositPlayer(player, 1000);
        }
    }
}