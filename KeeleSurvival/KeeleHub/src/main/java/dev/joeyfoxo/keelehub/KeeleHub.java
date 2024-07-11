package dev.joeyfoxo.keelehub;

import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleHub extends JavaPlugin {

    public static KeeleHub keeleHub;


    @Override
    public void onEnable() {
        keeleHub = this;
        new ListenerManager();
        new CommandManager();
        keeleHub.getServer().getWorlds().forEach(world -> world.setDifficulty(Difficulty.EASY));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
