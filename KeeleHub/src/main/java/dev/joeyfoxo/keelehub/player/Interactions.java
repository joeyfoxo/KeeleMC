package dev.joeyfoxo.keelehub.player;

import com.destroystokyo.paper.MaterialTags;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.util.GUI.GUI;
import dev.joey.keelecore.util.GUI.GUIRegistry;
import dev.joey.keelecore.util.ItemTagHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class Interactions implements Listener {

    public Interactions() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onBreak", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
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
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
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
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onDrop", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onPickup", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onInteract", event)) {
            System.out.println("[Interactions] Player " + player.getName() + " attempted an interaction without required rank.");
            System.out.println("[Interactions] Required rank: " + PlayerRank.ADMIN.name() + ", Player's rank: " + player.getRank().name());
            System.out.println("[Interactions] Event: " + event.getEventName() + ", Action: " + event.getAction());
            event.setCancelled(true);
        }

        ItemStack clicked = event.getItem();

        // Check if the clicked item is a special GUI inventory item by tag
        if (ItemTagHandler.hasTag(clicked, "inventory_item", PersistentDataType.STRING)) {
            String type = ItemTagHandler.getTag(clicked, "inventory_item", PersistentDataType.STRING);
            event.setCancelled(true); // Prevent normal use of GUI items

            switch (type) {
                case "hubselector" -> {
                    GUI hubSelector = GUIRegistry.getGUI("hubselector", player.getPlayer());
                    if (hubSelector != null) {
                        hubSelector.open(event.getPlayer());
                        return;
                    }
                }
                default -> {

                }
            }
            return; // Prevent further interaction processing after GUI open
        }

        // Cancel interaction with specific blocks on right click
        if (event.getClickedBlock() != null && event.getAction().isRightClick()) {
            var clickedType = event.getClickedBlock().getType();

            if (MaterialTags.DOORS.isTagged(clickedType)
                    || MaterialTags.FENCE_GATES.isTagged(clickedType)
                    || MaterialTags.BEDS.isTagged(clickedType)
                    || clickedType.toString().contains("FRAME")
                    || clickedType.toString().contains("BUTTON")
                    || clickedType.toString().contains("CHEST")) {
                event.setCancelled(true);
                return;
            }

            // Cancel if player is holding a lava-related item
            ItemStack item = event.getItem();
            if (item != null && item.getType().toString().contains("LAVA")) {
                event.setCancelled(true);
            }
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onEntityInteract", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // Damager might not always be a Player, guard this
        if (!(event.getDamager() instanceof org.bukkit.entity.Player damager)) return;

        KeelePlayer player = PlayerPermManager.getCached(damager.getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onDamage", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getPlayer().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onIgnite", event)) {
            event.setCancelled(true);
        }
    }

    @RequireRank(PlayerRank.ADMIN)
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        KeelePlayer player = PlayerPermManager.getCached(event.getEntity().getUniqueId());
        if (!RankGuard.hasRequiredRank(player, this, "onExplode", event)) {
            event.setCancelled(true);
        }
    }
}