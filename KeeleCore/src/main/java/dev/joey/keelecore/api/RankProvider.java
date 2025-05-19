package dev.joey.keelecore.api;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import io.javalin.Javalin;

public class RankProvider {

    Javalin api;

    public RankProvider() {
        api = Javalin.create();
        api.post("api/all-ranks", ctx -> {
            ctx.result(PlayerRank.listRanks());
        });

        api.get("/get-all-ranks", ctx -> {
            ctx.result(PlayerRank.listRanks());
        });

        // Optional health route
        api.get("/", ctx -> ctx.result("Rank API is running"));

        api.start(5005);
        System.out.println("✅ Javalin API started on port 5005");
    }
}
