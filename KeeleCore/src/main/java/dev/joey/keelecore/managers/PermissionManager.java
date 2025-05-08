package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class PermissionManager {

    private static DBManager manager;
    private static final Map<UUID, KeelePlayer> playerCache = new ConcurrentHashMap<>();

    public static void init(JavaPlugin plugin) {
        manager = new DBManager(plugin);
    }

    public static CompletableFuture<KeelePlayer> getPlayer(UUID uuid) {
        KeelePlayer cached = playerCache.get(uuid);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        return manager.loadPlayerAsync(uuid).thenApply(player -> {
            if (player != null) {
                playerCache.put(uuid, player);
            }
            return player;
        });
    }

    public static CompletableFuture<KeelePlayer> put(KeelePlayer player) {
        UUID uuid = player.getUuid();

        // Put in cache immediately
        playerCache.put(uuid, player);

        // Save to DB asynchronously
        manager.savePlayerDataAsync(uuid, player.getName(), player.getRank(), player.isVanished());

        return CompletableFuture.completedFuture(player);
    }

    public static void remove(UUID uuid) {
        playerCache.remove(uuid);
        //manager.deletePlayerAsync(player);
    }

    public static boolean hasCached(UUID uuid) {
        return playerCache.containsKey(uuid);
    }

    public static KeelePlayer getCached(UUID uuid) {
        return playerCache.get(uuid);
    }

    public static Map<UUID, KeelePlayer> getCache() {
        return playerCache;
    }

    public static void setVanished(Player player, boolean vanished) {
        UUID uuid = player.getUniqueId();
        KeelePlayer vanishedKP = getCached(uuid);
        if (vanishedKP == null) return;

        for (UUID otherUUID : playerCache.keySet()) {
            if (otherUUID.equals(uuid)) continue;

            Player otherPlayer = Bukkit.getPlayer(otherUUID);
            KeelePlayer observerKP = getCached(otherUUID);

            if (otherPlayer == null || observerKP == null) continue;

            boolean canSee = false;

            if (observerKP.getRank().isStaff()) {
                canSee = observerKP.getRank().hasPermissionLevel(vanishedKP.getRank());
            }

            if (vanished) {
                if (!canSee) {
                    otherPlayer.hidePlayer(KeeleCore.getInstance(), player);
                } else {
                    otherPlayer.showPlayer(KeeleCore.getInstance(), player);
                }
            } else {
                // Always show on unvanish
                otherPlayer.showPlayer(KeeleCore.getInstance(), player);
            }
        }

        vanishedKP.setVanished(vanished);
        //});
    }

    public static boolean isVanished(Player player) {
        KeelePlayer kp = getCached(player.getUniqueId());
        return kp != null && kp.isVanished();
    }

//    public static void getVanishValueFromDatabaseAsync(Player player, Consumer<Boolean> callback) {
//        getPlayer(player.getUniqueId())
//                .thenAccept(kp -> {
//                    boolean isVanished = kp != null && kp.isVanished();
//                    callback.accept(isVanished);
//                });
//    }
//
//    public static void getRank(Player player) {
//        UUID uuid = player.getUniqueId();
//
//        getPlayer(uuid).thenCompose(loaded -> {
//            KeelePlayer kp = (loaded != null) ? loaded : new KeelePlayer(player);
//            kp.setPlayer(player);
//            System.out.println("[DB] Loaded player data for " + player.getName() + " (" + uuid + ") with rank " + kp.getRank().name() + " BEFORE");
//
//            return put(kp).thenApply(updated -> {
//                updateDisplayNames(player, updated.getRank());
//                System.out.println("[DB] Finalized player rank for " + player.getName() + " as " + updated.getRank().name());
//                return updated;
//            });
//        });
//    }

    public static void setRank(Player player, PlayerRank newRank) {
        UUID uuid = player.getUniqueId();

        getPlayer(uuid).thenCompose(loaded -> {
            KeelePlayer kp = (loaded != null) ? loaded : new KeelePlayer(player);
            kp.setPlayer(player);
            kp.setName(player.getName());
            kp.setRank(newRank);

            System.out.println("[DB] Setting rank for " + player.getName() + " (" + uuid + ") to " + newRank.name());

            return put(kp).thenApply(updated -> {
                NameTagFormatting.updateNameTag(player, updated.getRank());
                System.out.println("[DB] Rank updated for " + player.getName() + " to " + updated.getRank().name());
                return updated;
            });
        });
    }

    public static void setRank(Player player, String input) {
        setRank(player, PlayerRank.valueOf(input.toUpperCase()));
    }
}
