package dev.joeyfoxo.keelehub.player;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class Interactions implements Listener {

    public Interactions() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void weatherControl(WeatherChangeEvent event) {

        if (event.toWeatherState()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void thunderControl(ThunderChangeEvent event) {

        if (event.toThunderState()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void timeControl(BlockCanBuildEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setBuildable(false);
        }
    }

    @EventHandler
    public void entitySpawning(CreatureSpawnEvent event) {

        event.setCancelled(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND
                && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDrop(PlayerAttemptPickupItemEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getClickedBlock() != null && event.getAction().isRightClick()) {

            if (MaterialTags.DOORS.isTagged(event.getClickedBlock().getType()) ||
                    MaterialTags.FENCE_GATES.isTagged(event.getClickedBlock().getType())
                    || MaterialTags.BEDS.isTagged(event.getClickedBlock().getType())
                    || event.getClickedBlock().getType().toString().contains("FRAME")
                    || event.getClickedBlock().getType().toString().contains("BUTTON")
                    || event.getClickedBlock().getType().toString().contains("CHEST")) {

                event.setCancelled(true);

            }

            if (event.getItem() != null && event.getItem().getType().toString().contains("LAVA")) {
                event.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!event.getDamager().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

}
