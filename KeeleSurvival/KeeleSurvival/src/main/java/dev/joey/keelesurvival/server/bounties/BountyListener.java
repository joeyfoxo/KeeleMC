package dev.joey.keelesurvival.server.bounties;

import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import dev.joey.keelesurvival.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.server.bounties.Bounty.bountyInventory;
import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class BountyListener implements Listener {

    public static Map<UUID, Double> sortedBounties = Util.sortByValue(Bounty.playerBounties, false);


    public BountyListener() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
        Bukkit.getScheduler().runTaskTimer(keeleSurvival, this::addItemsToBounty, 0, 200);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player victim = event.getPlayer();
        Player killer = victim.getKiller();

        if (!Bounty.hasBounty(victim) || killer == null || victim == killer) {
            return;
        }

        double bounty = Bounty.getBounty(victim.getUniqueId());
        if (getEconomy().hasAccount(killer)) {
            getEconomy().depositPlayer(killer, bounty);

            UtilClass.sendPlayerMessage(killer,
                    "You have claimed the " + Storage.getPrefix() +
                            Bounty.getBounty(victim.getUniqueId())
                            + " bounty on " + victim.getName(), UtilClass.success);

            UtilClass.sendPlayerMessage(victim,
                    killer.getName()
                            + " has claimed the " + Storage.getPrefix() +
                            Bounty.getBounty(victim.getUniqueId())
                            + " bounty on your head", UtilClass.success);
            Bounty.removeBounty(victim.getUniqueId());
            sortedBounties = Util.sortByValue(Bounty.playerBounties, false);
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == bountyInventory) {
            event.setCancelled(true);
        }


    }

    private void addItemsToBounty() {

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        meta.displayName(Component.text().content("").build());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE);
        glass.setItemMeta(meta);
        sortedBounties = Util.sortByValue(Bounty.playerBounties, false);


        for (int i = 0; i < sortedBounties.keySet().size(); i++) {
            bountyInventory.setItem(10 + i, applySkinToHead(sortedBounties.keySet().stream().toList().get(i),
                    sortedBounties.values().stream().toList().get(i)));
        }

        bountyInventory.setItem(0, glass);
        bountyInventory.setItem(1, glass);
        bountyInventory.setItem(2, glass);
        bountyInventory.setItem(3, glass);
        bountyInventory.setItem(4, glass);
        bountyInventory.setItem(5, glass);
        bountyInventory.setItem(6, glass);
        bountyInventory.setItem(7, glass);
        bountyInventory.setItem(8, glass);
        bountyInventory.setItem(17, glass);
        bountyInventory.setItem(26, glass);
        bountyInventory.setItem(35, glass);
        bountyInventory.setItem(44, glass);
        bountyInventory.setItem(9, glass);
        bountyInventory.setItem(18, glass);
        bountyInventory.setItem(27, glass);
        bountyInventory.setItem(36, glass);
        bountyInventory.setItem(45, glass);
        bountyInventory.setItem(46, glass);
        bountyInventory.setItem(47, glass);
        bountyInventory.setItem(48, glass);
        bountyInventory.setItem(49, glass);
        bountyInventory.setItem(50, glass);
        bountyInventory.setItem(51, glass);
        bountyInventory.setItem(52, glass);
        bountyInventory.setItem(53, glass);


    }

    private ItemStack applySkinToHead(UUID playerUUID, double bounty) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text().content("Bounty: " + Storage.getPrefix()+bounty).decoration(TextDecoration.ITALIC, false).color(TextColor.color(UtilClass.success)).build());

        if (player.getName() != null) {
            meta.displayName(Component.text().content(player.getName())
                    .decorate(TextDecoration.UNDERLINED)
                            .decoration(TextDecoration.ITALIC, false)
                    .color(TextColor.color(UtilClass.information)).build());
        }
        meta.lore(lore);
        meta.setOwningPlayer(player);
        skull.setItemMeta(meta);

        return skull;

    }

}
