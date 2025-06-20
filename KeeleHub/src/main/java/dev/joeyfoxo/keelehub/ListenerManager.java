package dev.joeyfoxo.keelehub;

import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joeyfoxo.keelehub.commands.ForceFieldCommand;
import dev.joeyfoxo.keelehub.hubselector.HubSelectorGUI;
import dev.joeyfoxo.keelehub.hubselector.HubSelectorItem;
import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.HungerCheck;
import dev.joeyfoxo.keelehub.player.Interactions;
import dev.joeyfoxo.keelehub.player.JoinAndLeaveEvents;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerManager {

    public ListenerManager() {

        ItemStack itemStack = new HubSelectorItem().getHubSelector();

        new HubSelectorGUI(itemStack);
        new JoinAndLeaveEvents(itemStack);
        new DoubleJump();
        new HungerCheck();
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
