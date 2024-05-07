package dev.joey.keelesurvival.server.events.withdraw;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.Util.keeleSurvival;
import static dev.joey.keelesurvival.util.Util.keeleSurvivalNameSpace;

public class BankNoteListener implements Listener {

    public BankNoteListener() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();

        if (item.getType() == Material.PAPER && meta.getPersistentDataContainer().has(
                new NamespacedKey(keeleSurvivalNameSpace, "bank_note"))) {
            BankNoteClickEvent banknoteClickEvent = new BankNoteClickEvent(event.getPlayer(), item);
            org.bukkit.Bukkit.getPluginManager().callEvent(banknoteClickEvent);
        }
    }


    @EventHandler
    public void onClaim(BankNoteClickEvent event) {
        Player player = event.getPlayer();
        ItemStack banknote = event.getBanknote();
        if (banknote == null) return;

        double balance = event.getBalanceOnNote();
        if (balance <= 0) return;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.isSimilar(banknote)) {
                getEconomy().depositPlayer(player, balance * item.getAmount());
                player.getInventory().removeItem(item);
            }
        }


    }
}