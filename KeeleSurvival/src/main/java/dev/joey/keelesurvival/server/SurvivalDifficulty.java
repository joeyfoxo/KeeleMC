package dev.joey.keelesurvival.server;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

public class SurvivalDifficulty {

    public SurvivalDifficulty() {
        for (World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.HARD);
        }

    }

}
