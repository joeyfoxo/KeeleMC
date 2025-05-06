package dev.joey.keelesurvival.server.chestprotection;

import com.destroystokyo.paper.ParticleBuilder;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Collection;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class ChestListener extends ChestLocking implements Listener {

    public ChestListener() {
        loadChestData();
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);

        Bukkit.getScheduler().runTaskTimer(keeleSurvival, () -> {

            if (!chests.isEmpty()) {

                for (Block block : chests) {

                    double X = block.getX();
                    double Y = block.getY();
                    double Z = block.getZ();
                    Location particleLocation = null;

                    if (block.getBlockData() instanceof Directional chest) {

                        if (chest.getFacing() == BlockFace.NORTH) {
                            particleLocation = new Location(block.getWorld(), X + 0.5, Y + 0.5, Z);
                        }
                        if (chest.getFacing() == BlockFace.EAST) {
                            particleLocation = new Location(block.getWorld(), X + 1, Y + 0.5, Z + 0.5);
                        }
                        if (chest.getFacing() == BlockFace.SOUTH) {
                            particleLocation = new Location(block.getWorld(), X + 0.5, Y + 0.5, Z + 1);
                        }
                        if (chest.getFacing() == BlockFace.WEST) {
                            particleLocation = new Location(block.getWorld(), X, Y + 0.5, Z + 0.5);
                        }
                    }

                    if (particleLocation != null) {
                        Collection<Player> nearbyPLayers = particleLocation.getNearbyPlayers(20);

                        new ParticleBuilder(Particle.DUST)
                                .color(255, 0, 0)
                                .count(5)
                                .location(particleLocation)
                                .receivers(nearbyPLayers)
                                .spawn();
                    }


                }
            }

        }, 0, 20);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.CHEST) {

            if (isLocked(event.getBlock())) {
                if (!isOwner(player, event.getBlock())) {
                    event.setCancelled(true);
                    UtilClass.sendPlayerMessage(player, "You don't have access to this chest", UtilClass.error);


                } else {
                    unlockChest(player, event.getBlock());
                }
            }

        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.CHEST) {

            if (!canPlayerPlaceChestAdjacent(player, block)) {
                event.setCancelled(true);
                UtilClass.sendPlayerMessage(player, "Sorry you cannot place a chest next to a locked chest", UtilClass.error);
                return;
            }

            getAdjacentBlocks(block).forEach(adjacentBlock -> {
                if (adjacentBlock.getType() == Material.CHEST) {
                    if (isLocked(adjacentBlock) && !isLocked(block)) {
                        lockChest(player, block);
                    }
                }

            });
        }
    }


    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getAction().isRightClick() && event.getClickedBlock() != null
                && event.getClickedBlock().getType() == Material.CHEST) {

            Block chest = event.getClickedBlock();

            if (isLocked(chest) && !hasAccess(player, chest)) {
                UtilClass.sendPlayerMessage(player, "You don't have access to this chest", UtilClass.error);
                event.setCancelled(true);
                return;
            }

            if (player.isSneaking()) {
                if (isDoubleChest(chest)) {
                    unlockOrLockDoubleChests(player, chest);
                    event.setCancelled(true);
                    return;
                }
                if (isLocked(chest) && isOwner(player, chest)) {
                    unlockChest(player, chest);
                } else {
                    lockChest(player, chest);
                }

                event.setCancelled(true);

            }

        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLoadChunk(ChunkLoadEvent event) {

        for (BlockState blockState : event.getChunk().getTileEntities()) {
            if (isLocked(blockState.getBlock())) {
                chests.add(blockState.getBlock());
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onLoadChunk(ChunkUnloadEvent event) {

        for (BlockState blockState : event.getChunk().getTileEntities()) {
            if (isLocked(blockState.getBlock())) {
                chests.remove(blockState.getBlock());
            }

        }
    }


}
