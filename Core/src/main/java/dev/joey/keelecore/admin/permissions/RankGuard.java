package dev.joey.keelecore.admin.permissions;


import dev.joey.keelecore.admin.permissions.player.KeelePlayer;

public class RankGuard {

    public static boolean hasRequiredRank(Object commandInstance, KeelePlayer player) {
        RequireRank annotation = commandInstance.getClass().getAnnotation(RequireRank.class);
        if (annotation == null) {
            return true; // no restriction
        }
        PlayerRank required = annotation.value();
        return player.getRank().hasPermissionLevel(required);
    }
}