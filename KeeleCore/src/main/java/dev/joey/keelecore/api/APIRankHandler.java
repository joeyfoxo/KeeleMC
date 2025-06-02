package dev.joey.keelecore.api;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import io.javalin.http.Context;

import java.util.Map;
import java.util.UUID;


public class APIRankHandler {

    public static void getAllRanks(Context ctx) {
        ctx.json(PlayerRank.listRanks());
    }

    public static void healthCheck(Context ctx) {
        ctx.result("Rank API is running");
    }

    public static void getPlayerRank(Context ctx) {
        try {
            UUID uuid = UUID.fromString(ctx.pathParam("uuid"));
            KeelePlayer player = PermissionManager.getCached(uuid);
            PlayerRank rank = player != null ? player.getRank() : null;

            if (rank != null) {
                ctx.json(Map.of("uuid", uuid, "rank", rank.name().toLowerCase()));
            } else {
                ctx.status(404).json(Map.of("error", "Player not found"));
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", "Invalid UUID format"));
        }
    }

}
