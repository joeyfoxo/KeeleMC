package dev.joeyfox.cravingChaos.world;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Location;

import java.util.Random;

public class WorldGenerator {

    public static World generateEmptyGlassWorld(String worldName) {
        // Create a void world with a custom chunk generator
        WorldCreator creator = new WorldCreator(worldName);
        creator.generator(new VoidChunkGenerator());
        creator.type(WorldType.FLAT);
        creator.generateStructures(false);
        World world = creator.createWorld();

        if (world == null) throw new IllegalStateException("World creation failed");

        buildGlassBox(world, new Location(world, 0, 100, 0), 100, 200); // height=100, width=200
        return world;
    }

    private static void buildGlassBox(World world, Location center, int height, int size) {
        int half = size / 2;
        for (int x = -half; x <= half; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = -half; z <= half; z++) {
                    boolean isWall = (x == -half || x == half || z == -half || z == half || y == 0 || y == height);
                    if (isWall) {
                        world.getBlockAt(center.clone().add(x, y, z)).setType(Material.GLASS);
                    } else {
                        world.getBlockAt(center.clone().add(x, y, z)).setType(Material.AIR);
                    }
                }
            }
        }
    }

    // Prevents terrain generation
    public static class VoidChunkGenerator extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
            return createChunkData(world); // Return completely empty chunk
        }
    }
}