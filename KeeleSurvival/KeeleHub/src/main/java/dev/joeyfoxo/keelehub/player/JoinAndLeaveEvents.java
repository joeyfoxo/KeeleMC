package dev.joeyfoxo.keelehub.player;

import dev.joeyfoxo.keelehub.hubselector.HubSelectorGUI;
import dev.joeyfoxo.keelehub.hubselector.HubSelectorItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class JoinAndLeaveEvents implements Listener {

    ItemStack selector;

    public JoinAndLeaveEvents(ItemStack selector) {
        this.selector = selector;
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @EventHandler
            (priority = EventPriority.HIGHEST)
    private void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setAllowFlight(true); // Allow flight cause we will double jump on flight attempt.
        event.joinMessage(null);

        player.teleport(player.getWorld().getSpawnLocation().toCenterLocation());

        player.getInventory().setItem(4, selector);
        new HubSelectorGUI(selector).addItemsToGUI();


    }

    @EventHandler
            (priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        leave(event.getPlayer());
        event.quitMessage(null);
    }

    @EventHandler
    public void quit(PlayerKickEvent event) {
        leave(event.getPlayer());
    }

    private void leave(Player player) {
        if (player == null)
            return;

        if (player.getGameMode() != GameMode.CREATIVE) //This line might cause issue's with other plugins
            player.setAllowFlight(false);
    }
}
