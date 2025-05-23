package dev.joey.keelecore;

import dev.joey.keelecore.api.RankProvider;
import dev.joey.keelecore.armour.galaxy.ColorCycleTask;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class KeeleCore extends JavaPlugin {

    // Singleton instance
    private static KeeleCore instance;
    private RankProvider rankProvider;

    // Expose globally
    public static KeeleCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Set singleton
        instance = this;
        PermissionManager.init(this);
        this.rankProvider = new RankProvider();

        UtilClass.keeleCore = this;
        saveDefaultConfig();

        new CommandManager();
        new ListenerManager();

        Bukkit.getMessenger().registerOutgoingPluginChannel(KeeleCore.getInstance(), "keele:rank");

        // Every 2 Minutes
        Bukkit.getScheduler().runTaskTimer(this, this::saveData, 0, TimeUnit.MINUTES.toSeconds(2) * 20);
        Bukkit.getScheduler().runTaskTimer(this, new ColorCycleTask(), 0L, 2L); // every 5 ticks
    }

    @Override
    public void onDisable() {
        saveData();
        saveConfig();
    }

    private void saveData() {



    }
}