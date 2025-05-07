package dev.joeyfox.cravingChaos.world;

import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class WorldGenerator {

    public static World generateEmptyGlassWorld(String worldName) {
        // Unload and delete existing world if it exists
        World existing = Bukkit.getWorld(worldName);
        if (existing != null) {
            Bukkit.unloadWorld(existing, false);
        }

        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        boolean isFreshWorld = false;

        if (worldFolder.exists()) {
            deleteDirectory(worldFolder);
            isFreshWorld = true;
        }

        // Create a void world with custom chunk generator
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(World.Environment.NORMAL);
        creator.generator(new VoidChunkGenerator());
        creator.generateStructures(false);
        World world = creator.createWorld();

        if (world == null) throw new IllegalStateException("World creation failed");

        if (isFreshWorld) {
            // Build glass box asynchronously to avoid blocking server startup
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("CravingChaos")),
                    () -> buildGlassBox(world, new Location(world, 0, 100, 0), 50, 50),
                    20L);
        }

        return world;
    }

    private static void buildGlassBox(World world, Location center, int height, int size) {
        int half = size / 2;
        Location mutableLoc = new Location(world, 0, 0, 0);

        for (int x = -half; x <= half; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = -half; z <= half; z++) {
                    boolean isWall = (x == -half || x == half || z == -half || z == half || y == 0);
                    if (isWall) {
                        mutableLoc.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                        world.getBlockAt(mutableLoc).setType(Material.GLASS, false);
                    }
                }
            }
        }
    }

    private static void deleteDirectory(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

    public static class VoidChunkGenerator extends ChunkGenerator {
        @Override
        public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random,
                                                    int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
            return createChunkData(world); // Empty chunk
        }
    }
}
