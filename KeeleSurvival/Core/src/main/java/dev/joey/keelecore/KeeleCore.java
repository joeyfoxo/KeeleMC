package dev.joey.keelecore;

import dev.joey.keelecore.admin.vanish.VanishCommand;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class KeeleCore extends JavaPlugin {

    ConfigFileHandler configFileHandler = new ConfigFileHandler(this);

    public static List<String> keeleStudent = new ArrayList<>();
    public static List<String> nonStudent = new ArrayList<>();

    @Override
    public void onEnable() {
        UtilClass.keeleCore = this;
        saveDefaultConfig();
        configFileHandler.createPlayerFile();
        if (!Bukkit.getScoreboardManager().getMainScoreboard().getTeams().isEmpty()) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        }

        keeleStudent.addAll(configFileHandler.getPlayerFile().getStringList("players.students"));
        nonStudent.addAll(configFileHandler.getPlayerFile().getStringList("players.guests"));
        new CommandManager();
        new ListenerManager();

        //Every 2 Minutes
        Bukkit.getScheduler().runTaskTimer(this, this::saveData, 0, TimeUnit.MINUTES.toSeconds(2) * 20);



    }

    @Override
    public void onDisable() {
        saveData();
        saveConfig();
    }


    private void saveData() {
        configFileHandler.getPlayerFile().set("vanished", VanishCommand.getVanishedPlayers());
        configFileHandler.getPlayerFile().set("players.students", keeleStudent);
        configFileHandler.getPlayerFile().set("players.guests", nonStudent);
        configFileHandler.saveFile();
    }
}
