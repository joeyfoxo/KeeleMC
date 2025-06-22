package dev.joeyfox.skyScraper.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class SkyWorldGenerator {

    public SkyWorldGenerator() {

        WorldCreator creator = new WorldCreator("circle_world");
        creator.generator(new VoidWorldGenerator());
        World world = creator.createWorld();
    }

}
