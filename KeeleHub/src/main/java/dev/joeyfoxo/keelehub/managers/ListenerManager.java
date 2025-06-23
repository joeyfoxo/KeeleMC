package dev.joeyfoxo.keelehub.managers;

import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joeyfoxo.keelehub.Interactables.ItemListener;
import dev.joeyfoxo.keelehub.KeeleHub;
import dev.joeyfoxo.keelehub.commands.ForceFieldCommand;
import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.Interactions;
import dev.joeyfoxo.keelehub.player.JoinAndLeaveEvents;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerManager {

    public ListenerManager() {

        ItemManager itemManager = new ItemManager();

        new ItemListener();
        new JoinAndLeaveEvents(itemManager.getHubSelectorItem());
        new DoubleJump();
        new Interactions();

        //Just a tickover for the server to run every half second
        new BukkitRunnable() {

            @Override
            public void run() {

                for (KeelePlayer keelePlayer : PlayerPermManager.getPlayers().values()) {
                    if (!keelePlayer.getPlayer().isOnline()) {
                        PlayerPermManager.remove(keelePlayer.getUuid());
                        continue;
                    }

                    if (keelePlayer.getPlayer() == null || !keelePlayer.getPlayer().isValid()) {
                        continue;
                    }

                    Player player = keelePlayer.getPlayer();

                    player.setFoodLevel(20);


                    //FORCEFIELD CHECK

                    if (!keelePlayer.isForceFieldEnabled()) {
                        continue;
                    }
                    for (Entity nearbyEntities : keelePlayer.getPlayer().getNearbyEntities(4, 4, 4)) {
                        if (nearbyEntities instanceof Player nearPlayer) {
                            ForceFieldCommand.forceFieldCheck(keelePlayer,
                                    PlayerPermManager.getCached(nearPlayer.getUniqueId()));
                        }
                    }


                }

            }
        }.runTaskTimer(KeeleHub.keeleHub, 0, 10); // Run every second

    }
}
