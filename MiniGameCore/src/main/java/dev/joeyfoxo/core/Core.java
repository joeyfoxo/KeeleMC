package dev.joeyfoxo.core;

import dev.joey.keelecore.util.UtilClass;
import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.CoreGame;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static org.codehaus.plexus.util.FileUtils.deleteDirectory;

/**
 * Core class for the KeeleMC plugin.
 * This class extends the JavaPlugin class from the Bukkit API, and is the main class for the plugin.
 * It is responsible for setting up the game when the plugin is enabled, and cleaning up when the plugin is disabled.
 *
 * @param <G> The type of game this core class is managing. Must extend CoreGame.
 */
public abstract class Core<G extends CoreGame<G>> extends JavaPlugin {
    private static JavaPlugin keeleMiniCore;
    private G game;

    @Override
    public void onEnable() {
        setKeeleMiniCore(this);
        this.game = createGameInstance();
        new CoreGameStart<>(game);
    }

    @Override
    public void onDisable() {
        if (game != null) {
            game.shutdown(); // Add this method in CoreGame subclasses
        }

        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().startsWith("game_")) continue;

            Bukkit.unloadWorld(world, false);
            File folder = new File(Bukkit.getWorldContainer(), world.getName());
            if (UtilClass.deleteDirectory(folder)) {
                getLogger().info("Deleted world: " + world.getName());
            }
        }
    }

    protected abstract G createGameInstance();

    public G getGame() {
        return game;
    }

    public static JavaPlugin getKeeleMiniCore() {
        return keeleMiniCore;
    }

    public static void setKeeleMiniCore(JavaPlugin plugin) {
        keeleMiniCore = plugin;
    }
}