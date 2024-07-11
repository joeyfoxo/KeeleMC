package dev.joey.keelecore.admin.vanish;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class VanishListener extends Vanish implements Listener {

    Inventory fakeInventory;
    Chest chest;

    public VanishListener() {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }

    @EventHandler
    public void onOpenChest(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (isVanished(player)
                && event.getAction().isRightClick()
                && event.getClickedBlock().getType() == Material.CHEST) {

            chest = (Chest) event.getClickedBlock().getState();

            fakeInventory = chest.getInventory();
            //fakeInventory.setItem(4, new ItemStack(Material.BARRIER));
            player.openInventory(fakeInventory);

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isVanished(player)) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onCloseChest(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();

        if (isVanished(player)
                && event.getInventory() == fakeInventory
                && chest != null) {

            chest.getInventory().setContents(fakeInventory.getContents());


        }

    }

    private static Location add(Location l, int x, int z) {
        return new Location(l.getWorld(), l.getX() + x, l.getY(), l.getZ() + z);
    }

    private static List<Location> getAdjacentBlockLocations(Location loc) {
        List<Location> adjacentBlockLocations = new ArrayList<>();
        adjacentBlockLocations.add(add(loc, 1, 0));
        adjacentBlockLocations.add(add(loc, -1, 0));
        adjacentBlockLocations.add(add(loc, 0, -1));
        adjacentBlockLocations.add(add(loc, 0, 1));
        adjacentBlockLocations.add(add(loc, 1, 1));
        adjacentBlockLocations.add(add(loc, -1, -1));
        adjacentBlockLocations.add(add(loc, 1, -1));
        adjacentBlockLocations.add(add(loc, -1, 1));
        return adjacentBlockLocations;
    }
}
