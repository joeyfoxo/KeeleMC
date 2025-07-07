package dev.joey.keelesurvival.server.events.sleeping;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class SleepController implements Listener {


    public SleepController() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {

        if (event.getEntity() instanceof Phantom phantom) {
            phantom.setSize(ThreadLocalRandom.current().nextInt(0, 5));
            phantom.setTarget(null);
        }

    }
}
