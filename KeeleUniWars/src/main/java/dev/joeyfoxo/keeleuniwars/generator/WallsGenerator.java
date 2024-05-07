package dev.joeyfoxo.keeleuniwars.generator;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;
import dev.joeyfoxo.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class WallsGenerator {

    public static int wallSize = 300;
    int minHeight;
    int maxHeight;
    World world;
    EditSession session;
    public static HashMap<Location, Block> wallsLocation = new HashMap<>();
    public static HashSet<Location> outerWallLocation = new HashSet<>();
    public static HashSet<Location> surfaceBlockLocation = new HashSet<>();
    public static int center = 0;

    public WallsGenerator() {
        world = Bukkit.getWorlds().get(0);
        minHeight = world.getMinHeight();
        maxHeight = world.getMaxHeight();
        session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);

        loadChunkAsync();
    }

    public void dropTheWalls() {
        new BukkitRunnable() {
            int y = world.getMaxHeight() - 1; // Start from the top layer

            @Override
            public void run() {
                if (y < world.getMinHeight()) {
                    this.cancel(); // Stop the task when all layers have been removed
                    return;
                }

                for (Location location : wallsLocation.keySet()) {
                    if (outerWallLocation.contains(location)) {
                        continue;
                    }

                    if (surfaceBlockLocation.contains(location)) {
                        Block surfaceBlock = wallsLocation.get(location);
                        if (surfaceBlock.getY() == y) {
                            surfaceBlock.setType(Material.GRASS_BLOCK);
                        } else if (surfaceBlock.getY() < y && surfaceBlock.getY() > world.getMinHeight()) {
                            surfaceBlock.setType(Material.DIRT);
                        }
                        continue;
                    }

                    Block block = wallsLocation.get(location);
                    if (block.getY() == y) {
                        block.setType(Material.AIR);
                    }
                }

                y--; // Move to the next layer
            }
        }.runTaskTimer(Core.getKeeleMiniCore(), 0L, 10L); // Schedule the task to run every second (20 ticks)
    }

    private void loadChunkAsync() {
        int halfSize = wallSize / 2;

        // Coordinates for the corners of the area
        int minX = center - halfSize;
        int minZ = center - halfSize;
        int maxX = center + halfSize;
        int maxZ = center + halfSize;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                // Load chunk asynchronously
                world.getChunkAtAsync(x >> 4, z >> 4, true);
            }
        }
    }

    public boolean setupWallsGameArea(int centerX, int centerZ) {
        int halfSize = wallSize / 2;

        // Coordinates for the corners of the area
        int minX = centerX - halfSize;
        int minZ = centerZ - halfSize;
        int maxX = centerX + halfSize;
        int maxZ = centerZ + halfSize;

        if (!findClosestSuitableArea(centerX, centerZ)) {
            return false;
        }

        // Create the walls
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (x == centerX || z == centerZ) {
                    for (int y = minHeight; y <= maxHeight; y++) {
                        BlockVector3 position = BlockVector3.at(x, y, z);
                        Block currentBlock = world.getBlockAt(x, y, z);
                        Block aboveBlock = world.getBlockAt(x, y + 1, z);
                        if (aboveBlock.getType() == Material.AIR && currentBlock.getType() != Material.AIR) {
                            if (world.getBlockAt(currentBlock.getLocation()).getType() == Material.TALL_GRASS) {
                                surfaceBlockLocation.add(currentBlock.getLocation().subtract(0, 2, 0));
                            } else {
                                surfaceBlockLocation.add(currentBlock.getLocation());
                            }
                        }
                        wallsLocation.put(new Location(world, x, y, z), currentBlock);
                        session.setBlock(position, BlockTypes.BEDROCK.getDefaultState());
                    }
                }


                if (x == minX || x == maxX || z == minZ || z == maxZ) {
                    for (int y = world.getMinHeight(); y <= world.getMaxHeight(); y++) {
                        BlockVector3 position = BlockVector3.at(x, y, z);
                        session.setBlock(position, BlockTypes.BEDROCK.getDefaultState());
                        outerWallLocation.add(new Location(world, x, y, z));
                    }
                }

                session.setBlock(x, maxHeight - 1, z, BlockTypes.BARRIER.getDefaultState());
            }
        }
        session.close();
        return true;
    }


    public boolean findClosestSuitableArea(int currentX, int currentZ) {
        // Check the biome at the current center
        Biome currentBiome = world.getBiome(currentX, world.getHighestBlockYAt(currentX, currentZ), currentZ);
        if (currentBiome != Biome.OCEAN && currentBiome
                != Biome.DEEP_OCEAN && currentBiome
                != Biome.WARM_OCEAN && currentBiome
                != Biome.LUKEWARM_OCEAN && currentBiome
                != Biome.COLD_OCEAN && currentBiome
                != Biome.DEEP_LUKEWARM_OCEAN && currentBiome
                != Biome.DEEP_COLD_OCEAN && currentBiome
                != Biome.DEEP_FROZEN_OCEAN) {
            return true; // Return true if the current center is suitable
        }

        Random random = new Random();
        int radius = 1000; // Define the radius for the nearby area
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            // Generate a random point within the radius
            double angle = 2 * Math.PI * random.nextDouble();
            int x = currentX + (int) (radius * Math.cos(angle));
            int z = currentZ + (int) (radius * Math.sin(angle));

            // Check the biome at the point
            Biome biome = world.getBiome(x, world.getHighestBlockYAt(x, z), z);
            if (biome != Biome.OCEAN && biome
                    != Biome.DEEP_OCEAN && biome
                    != Biome.WARM_OCEAN && biome
                    != Biome.LUKEWARM_OCEAN && biome
                    != Biome.COLD_OCEAN && biome
                    != Biome.DEEP_LUKEWARM_OCEAN && biome
                    != Biome.DEEP_COLD_OCEAN && biome
                    != Biome.DEEP_FROZEN_OCEAN) {
                double distance = Math.sqrt(Math.pow(x - currentX, 2) + Math.pow(z - currentZ, 2));
                if (distance < closestDistance) {
                    WallsGenerator.center = x; // Adjust the center
                    closestDistance = distance;
                }
            }
        }

        return closestDistance != Double.MAX_VALUE; // Return true if a suitable area was found
    }
}