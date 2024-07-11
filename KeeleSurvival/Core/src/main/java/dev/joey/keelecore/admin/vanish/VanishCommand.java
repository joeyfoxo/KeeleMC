package dev.joey.keelecore.admin.vanish;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.joey.keelecore.KeeleCore.nonStudent;
import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class VanishCommand extends Vanish implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.vanish"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }
        if (args.length == 1) {
            player = Bukkit.getPlayer(args[0]);
        }

        playerNullCheck(player, player);
        if (vanishedPlayers.contains(player.getUniqueId().toString())) { //UNVANISH
            vanishedPlayers.remove(player.getUniqueId().toString());
            for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                playersOnServer.showPlayer(keeleCore, player);
            }
            player.getInventory().setHelmet(null);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            UtilClass.sendPlayerMessage(player, "You are now visible", UtilClass.success);

        } else { //VANISH
            vanishedPlayers.add(player.getUniqueId().toString());
            for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                if (!(hasPermissionToSee(playersOnServer))) {
                    playersOnServer.hidePlayer(keeleCore, player);
                }
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 1, false, false, false));
            applyHead(player);
            UtilClass.sendPlayerMessage(player, "You are now vanished", UtilClass.success);
        }
        return false;
    }

    private void applyHead(Player player) {
        if (player.getInventory().getHelmet() == null) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(player);
            head.setItemMeta(meta);
            player.getInventory().setHelmet(head);
        }
    }


    //TODO: Add listener for joining and leaving

}
