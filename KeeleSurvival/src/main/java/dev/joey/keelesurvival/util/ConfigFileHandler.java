package dev.joey.keelesurvival.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class ConfigFileHandler {

    static File chestFile;
    static FileConfiguration chestConfig;

    static File playerFile;
    static FileConfiguration playerConfig;

    public FileConfiguration getChestFile() {
        return chestConfig;
    }
    public FileConfiguration getPlayerFile() {
        return playerConfig;
    }

    public void createChestFile() {

        chestFile = new File(keeleSurvival.getDataFolder(), "chests.yml");
        if (!chestFile.exists()) {
            chestFile.getParentFile().mkdirs();
            keeleSurvival.saveResource("chests.yml", false);
        }

        chestConfig = new YamlConfiguration();
        try {
            chestConfig.load(chestFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveFiles() {

        try {
            chestConfig.save(chestFile);
            playerConfig.save(playerFile);
        } catch (IOException e) {
            System.out.println("Couldn't save the file");
        }

    }

    public void createPlayerFile() {

        playerFile = new File(keeleSurvival.getDataFolder(), "players.yml");
        if (!playerFile.exists()) {
            playerFile.getParentFile().mkdirs();
            keeleSurvival.saveResource("players.yml", false);
        }

        playerConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

}
