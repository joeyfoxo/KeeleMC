package dev.joeyfox.cravingChaos.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;

public class WorldGenerator {

    public static void buildGlassBox(World world, Location center, int height, int size) {
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

    public static void deleteDirectory(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

    public static void unloadAndDeleteWorld(String worldName) {
        World existing = Bukkit.getWorld(worldName);
        if (existing != null) {
            Bukkit.unloadWorld(existing, false);
        }

        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        if (worldFolder.exists()) {
            deleteDirectory(worldFolder);
        }
    }
}