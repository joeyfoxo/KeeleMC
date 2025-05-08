package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.admin.permissions.RequireRank;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequireRank(PlayerRank.MOD)
public class BanlistCommand extends SuperCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        KeelePlayer kPlayer = PermissionManager.getCached(player.getUniqueId());
        if (UtilClass.noAccessMessage(this, kPlayer)) return true;

        List<BanEntry> entries = Bukkit.getBanList(BanList.Type.NAME).getBanEntries().stream().toList();
        if (entries.isEmpty()) {
            UtilClass.sendPlayerMessage(kPlayer, "There are no banned players.", UtilClass.information);
            return true;
        }

        UtilClass.sendPlayerMessage(kPlayer, "⚠ Banned Players:", UtilClass.information);
        for (BanEntry entry : entries) {
            String bannedBy = entry.getSource();
            String reason = entry.getReason();
            Date expires = entry.getExpiration();

            // Try to get the staff rank
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getSource());
            KeelePlayer staff = PermissionManager.getCached(offlinePlayer.getUniqueId());

            Component msg = Component.text(" • ")
                    .append(Component.text(entry.getTarget() + " - " + reason + " "))
                    .append(Component.text("("))
                    .append(staff != null ? staff.getRank().getPrefix() : Component.text("[Unknown]", NamedTextColor.GRAY))
                    .append(Component.text(staff != null ? staff.getName() : bannedBy)) // fallback if staff is null
                    .append(Component.text(")"));

            UtilClass.sendPlayerMessage(kPlayer, msg);
        }

        return true;
    }
}