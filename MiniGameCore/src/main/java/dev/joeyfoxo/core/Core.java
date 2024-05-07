package dev.joeyfoxo.core;

import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.CoreGame;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class for the KeeleMC plugin.
 * This class extends the JavaPlugin class from the Bukkit API, and is the main class for the plugin.
 * It is responsible for setting up the game when the plugin is enabled, and cleaning up when the plugin is disabled.
 *
 * @param <G> The type of game this core class is managing. Must extend CoreGame.
 */
public class Core<G extends CoreGame<G>> extends JavaPlugin {

    private static JavaPlugin keeleMiniCore;

    /**
     * Called when the plugin is enabled.
     * Sets the static keeleMiniCore field to this instance and starts a new game.
     */
    @Override
    public void onEnable() {
        setKeeleMiniCore(this);
        G game = (G) CoreGame.getInstance();
        new CoreGameStart<>(game);
    }

    /**
     * Called when the plugin is disabled.
     * Currently, does nothing.
     */
    @Override
    public void onDisable() {

    }

    /**
     * Sets the static keeleMiniCore field to the given plugin instance.
     *
     * @param plugin The plugin instance to set keeleMiniCore to.
     */
    public static void setKeeleMiniCore(JavaPlugin plugin) {
        keeleMiniCore = plugin;
    }

    /**
     * Returns the static keeleMiniCore field.
     *
     * @return The static keeleMiniCore field.
     */
    public static JavaPlugin getKeeleMiniCore() {
        return keeleMiniCore;
    }
}