package dev.joeyfox.cravingChaos;

import dev.joeyfox.cravingChaos.game.CravingGame;
import dev.joeyfox.cravingChaos.game.CravingGameStart;
import dev.joeyfox.cravingChaos.game.CravingSettings;
import dev.joeyfox.cravingChaos.world.VoidWorldGen;
import dev.joeyfox.cravingChaos.world.WorldGenerator;
import dev.joeyfoxo.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public class CravingChaos extends Core<CravingGame> {

    @Override
    public void onEnable() {
        super.onEnable(); // This will call createGameInstance() inside Core
        Bukkit.getScheduler().runTask(this, () -> {
            WorldGenerator.unloadAndDeleteWorld( "glass");
            World world = Bukkit.createWorld(new WorldCreator("glass"));
            setKeeleMiniCore(this);
            new CravingGameStart(getGame());
                if (world != null) {
                    WorldGenerator.buildGlassBox(world, new Location(world, 0, 100, 0), CravingSettings.getCageHeight(), CravingSettings.getCageSize());
                }
            });
    }

    @Override
    protected CravingGame createGameInstance() {
        return new CravingGame();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidWorldGen();
    }
}
