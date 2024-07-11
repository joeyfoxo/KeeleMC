package dev.joeyfoxo.keeleuniwars.generator;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;
import dev.joeyfoxo.keeleuniwars.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static dev.joeyfoxo.keeleuniwars.game.Settings.wallSize;

public class WallsGenerator {

    int minHeight;
    int maxHeight;
    World world;
    EditSession session;
    public static HashMap<Location, Block> wallsLocation = new HashMap<>();
    public static HashSet<Location> outerWallLocation = new HashSet<>();
    public static HashSet<Location> surfaceBlockLocation = new HashSet<>();
    public static int center = 0;
    private boolean isAsyncRunning = false;

    public WallsGenerator() {
        world = Bukkit.getWorlds().get(0);
        minHeight = world.getMinHeight();
        maxHeight = world.getMaxHeight();
        session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);
        loadChunkAsync();
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

    private CompletableFuture<List<Location>> setupWallsAsync(int centerX, int centerZ) {
        isAsyncRunning = true;
        CompletableFuture<List<Location>> futureBlockLocations = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Util.keeleUniWars, () -> {
            List<Location> blockLocations = Collections.synchronizedList(new ArrayList<>());
            int halfSize = wallSize / 2;

            int minX = centerX - halfSize;
            int minZ = centerZ - halfSize;
            int maxX = centerX + halfSize;
            int maxZ = centerZ + halfSize;

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    for (int y = minHeight; y <= maxHeight; y++) {
                        if (x == centerX || z == centerZ || x == minX || x == maxX || z == minZ || z == maxZ) {
                            blockLocations.add(new Location(world, x, y, z));
                        }
                    }
                }
            }

            isAsyncRunning = false;
            futureBlockLocations.complete(blockLocations);
        });
        return futureBlockLocations;
    }

    public boolean setupWallsGameArea(int centerX, int centerZ) {

        int halfSize = wallSize / 2;

        int minX = centerX - halfSize;
        int minZ = centerZ - halfSize;
        int maxX = centerX + halfSize;
        int maxZ = centerZ + halfSize;

        if (!findClosestSuitableArea(centerX, centerZ)) {
            return false;
        }

        if (isAsyncRunning) {
            return false;
        }

        CompletableFuture<List<Location>> futureBlockLocations = setupWallsAsync(centerX, centerZ);

        futureBlockLocations.thenAccept(blockLocations -> blockLocations.forEach(blockLocation -> {

            int x = blockLocation.getBlockX();
            int y = blockLocation.getBlockY();
            int z = blockLocation.getBlockZ();
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
            if (x == centerX || z == centerZ) {
                wallsLocation.put(new Location(world, x, y, z), currentBlock);
            }

            if (x == minX || x == maxX || z == minZ || z == maxZ) {
                outerWallLocation.add(new Location(world, x, y, z));
            }

            session.setBlock(position, BlockTypes.BEDROCK);
            session.setBlock(x, maxHeight - 1, z, BlockTypes.BARRIER);
        })).whenComplete((blockLocations, throwable) -> session.close());

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