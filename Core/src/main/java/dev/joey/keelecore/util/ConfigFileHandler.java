package dev.joey.keelecore.util;

import dev.joey.keelecore.KeeleCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigFileHandler {

    static File playerFile;
    static FileConfiguration playerConfig;
    KeeleCore keeleCore;

    public ConfigFileHandler(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
    }

    public FileConfiguration getPlayerFile() {
        return playerConfig;
    }

    public void createPlayerFile() {

        playerFile = new File(keeleCore.getDataFolder(), "players.yml");
        if (!playerFile.exists()) {
            playerFile.getParentFile().mkdirs();
            keeleCore.saveResource("players.yml", false);
        }

        playerConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveFile() {

        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            System.out.println("Couldn't save the file");
        }

    }


}
