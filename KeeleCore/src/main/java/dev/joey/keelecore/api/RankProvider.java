package dev.joey.keelecore.api;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import io.javalin.Javalin;

public class RankProvider {

    public RankProvider() {
        Javalin api = Javalin.create();
        api.post("api/all-ranks", ctx -> {
            ctx.result(PlayerRank.listRanks());
        }).start(5005);
    }
}
