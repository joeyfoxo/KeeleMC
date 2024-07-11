package dev.joeyfoxo.keelehub.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class HungerCheck {

    public HungerCheck() {

        Bukkit.getScheduler().runTaskTimer(keeleHub, () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setFoodLevel(20);
            }

        }, 0, 100);

    }

}
