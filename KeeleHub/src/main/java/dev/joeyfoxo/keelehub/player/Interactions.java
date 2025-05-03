package dev.joeyfoxo.keelehub.player;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.event.entity.SkeletonHorseTrapEvent;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class Interactions implements Listener {

    public Interactions() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onBreak", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onPlace", event)) {
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

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void timeControl(BlockCanBuildEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "timeControl", event)) {
            event.setBuildable(false);
        }
    }

    @EventHandler
    public void entitySpawning(CreatureSpawnEvent event) {
        event.setCancelled(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND
                && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onDrop", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onPickup", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onInteract", event)) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedBlock() != null && event.getAction().isRightClick()) {
            if (MaterialTags.DOORS.isTagged(event.getClickedBlock().getType()) ||
                    MaterialTags.FENCE_GATES.isTagged(event.getClickedBlock().getType()) ||
                    MaterialTags.BEDS.isTagged(event.getClickedBlock().getType()) ||
                    event.getClickedBlock().getType().toString().contains("FRAME") ||
                    event.getClickedBlock().getType().toString().contains("BUTTON") ||
                    event.getClickedBlock().getType().toString().contains("CHEST")) {
                event.setCancelled(true);
            }

            if (event.getItem() != null && event.getItem().getType().toString().contains("LAVA")) {
                event.setCancelled(true);
            }
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onEntityInteract", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // Damager might not always be a Player, guard this
        if (!(event.getDamager() instanceof org.bukkit.entity.Player damager)) return;

        KeelePlayer player = PermissionManager.getCached(damager.getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onDamage", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onIgnite", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        KeelePlayer player = PermissionManager.getCached(event.getEntity().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onExplode", event)) {
            event.setCancelled(true);
        }
    }
}