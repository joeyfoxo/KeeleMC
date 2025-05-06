package dev.joey.keelesurvival;

import dev.joey.keelesurvival.managers.CommandManager;
import dev.joey.keelesurvival.managers.DataManager;
import dev.joey.keelesurvival.managers.ListenerManager;
import dev.joey.keelesurvival.server.bounties.Bounty;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.server.economy.provider.EconomyProvider;
import dev.joey.keelesurvival.server.events.enderdragon.ControlDragonSpawn;
import dev.joey.keelesurvival.server.events.headdrop.HeadEvent;
import dev.joey.keelesurvival.util.ConfigFileHandler;
import dev.joey.keelesurvival.util.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class KeeleSurvival extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    ConfigFileHandler configFileHandler = new ConfigFileHandler();

    @Override
    public void onEnable() {
        Util.keeleSurvival = this;

        configFileHandler.createChestFile();
        configFileHandler.createPlayerFile();

        for (World worlds : Bukkit.getWorlds()) {

            worlds.getWorldBorder().setCenter(0, 0);
            worlds.getWorldBorder().setSize(100000);

        }
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        saveDefaultConfig();
        new DataManager();
        new ListenerManager();
        new CommandManager();

    }

    @Override
    public void onDisable() {
//        for (Player player : Bukkit.getOnlinePlayers()) {
//            if (player != null) {
//                configFileHandler.getPlayerFile().set("player.balance." + player.getUniqueId(), Storage.getPlayerBalance().get(player.getUniqueId()));
//                configFileHandler.getPlayerFile().set("player.bounties." + player.getUniqueId(), Bounty.getPlayerBounties().get(player.getUniqueId()));
//                configFileHandler.getPlayerFile().set("player.kills." + player.getUniqueId(), HeadEvent.playerKills.get(player.getUniqueId()));
//            }
//        }
        getConfig().set("dragonProgressSeconds", ControlDragonSpawn.secondsElapsed);
        saveConfig();
        configFileHandler.saveFiles();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        getServer().getServicesManager().register(Economy.class, new EconomyProvider(), this, ServicePriority.Highest);
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
