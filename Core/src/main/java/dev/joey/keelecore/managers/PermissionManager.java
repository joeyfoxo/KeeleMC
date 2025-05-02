package dev.joey.keelecore.managers;

import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {

    private static final Map<UUID, KeelePlayer> playerCache = new HashMap<>();

    public static KeelePlayer put(KeelePlayer player) {
        player.getPlayer().playerListName(player.getRank().getPrefix().append(Component.text(player.getName())));
        return playerCache.put(player.getUuid(), player);
    }

    public static KeelePlayer get(UUID uuid) {
        return playerCache.get(uuid);
    }

    public static void remove(KeelePlayer player) {
        playerCache.remove(player.getUuid());
    }

    public static void clear() {
        playerCache.clear();
    }
}
