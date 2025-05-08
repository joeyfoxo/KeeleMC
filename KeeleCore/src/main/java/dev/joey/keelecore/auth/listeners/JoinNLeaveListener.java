package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class JoinNLeaveListener implements Listener {

    public JoinNLeaveListener() {
        KeeleCore.getPlugin(KeeleCore.class).getServer().getPluginManager().registerEvents(this, KeeleCore.getPlugin(KeeleCore.class));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionManager.getPlayer(player.getUniqueId()).thenAccept(keelePlayer -> {

            keelePlayer.setPlayer(player);
            PermissionManager.setVanished(player, keelePlayer.isVanished());
            NameTagFormatting.updateNameTag(player, keelePlayer.getRank());

            PermissionAttachment attachment = player.addAttachment(KeeleCore.getPlugin(KeeleCore.class));

            for (String permission : keelePlayer.getRank().getPermissions()) {
                attachment.setPermission(permission, true);
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        KeelePlayer player = PermissionManager.getCached(uuid);

        PermissionAttachment attachment = player.getPlayer().addAttachment(KeeleCore.getPlugin(KeeleCore.class));
        for (String permission : player.getRank().getPermissions()) {
            attachment.unsetPermission(permission);
        }
        player.getPlayer().removeAttachment(attachment);

        PermissionManager.put(player).thenRun(() -> PermissionManager.remove(player.getUuid()));
    }
}
