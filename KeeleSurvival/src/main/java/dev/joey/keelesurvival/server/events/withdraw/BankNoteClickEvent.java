package dev.joey.keelesurvival.server.events.withdraw;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static dev.joey.keelesurvival.util.Util.keeleSurvivalNameSpace;

public class BankNoteClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack banknote;

    public BankNoteClickEvent(Player player, ItemStack banknote) {
        this.player = player;
        this.banknote = banknote;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getBanknote() {
        return banknote;
    }

    public double getBalanceOnNote() {
        if (banknote.hasItemMeta() && banknote.getItemMeta().getPersistentDataContainer().has(
                new NamespacedKey(keeleSurvivalNameSpace, "bank_note"), PersistentDataType.DOUBLE)) {
            return banknote.getItemMeta().getPersistentDataContainer().get(
                    new NamespacedKey(keeleSurvivalNameSpace, "bank_note"), PersistentDataType.DOUBLE);
        }
        return 0;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}