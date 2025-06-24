package dev.joey.keelecore;

import dev.joey.keelecore.armour.galaxy.ColorCycleTask;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.managers.DBManager;
import dev.joey.keelecore.managers.ListenerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleCore extends JavaPlugin {

    // Singleton instance
    private static KeeleCore instance;
    //private RankProvider rankProvider;

    // Expose globally
    public static KeeleCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Set singleton
        if (instance == null) {
            instance = this;
        }
        saveDefaultConfig();
        saveConfig();

        new DBManager(this);


        new CommandManager();
        new ListenerManager();

        Bukkit.getMessenger().registerOutgoingPluginChannel(KeeleCore.getInstance(), "keele:rank");

        // Every 2 Minutes
        Bukkit.getScheduler().runTaskTimer(this, new ColorCycleTask(), 0L, 2L); // every 5 ticks
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}