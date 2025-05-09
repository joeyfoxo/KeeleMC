package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.RankGuard;
import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequireRank(PlayerRank.MOD)
public class VanishCommand extends SuperCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, keelePlayer)) {
            return true;
        }
        if (args.length == 1) {
            player = Bukkit.getPlayer(args[0]);
        }

        playerNullCheck(player, player);
        if (keelePlayer.isVanished()) { //UNVANISH
            PermissionManager.setVanished(player, false);
            keelePlayer.setVanished(false);
            player.getInventory().setHelmet(null);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            UtilClass.sendPlayerMessage(player, "You are now visible", UtilClass.success);

        } else { //VANISH
            PermissionManager.setVanished(player, true);
            keelePlayer.setVanished(true);
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }


    //TODO: Add listener for joining and leaving

}
