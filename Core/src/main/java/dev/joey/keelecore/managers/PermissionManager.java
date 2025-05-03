package dev.joey.keelecore.managers;

import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {

    private static final Map<UUID, KeelePlayer> playerCache = new HashMap<>();

    public static KeelePlayer put(KeelePlayer player) {
        playerCache.put(player.getUuid(), player); // store first
        player.getPlayer().playerListName(player.getRank().getPrefix().append(Component.text(player.getName())));
        NameTagFormatting.updateNameTag(player.getPlayer());
        return player;
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
