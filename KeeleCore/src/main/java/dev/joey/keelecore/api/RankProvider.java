package dev.joey.keelecore.api;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import io.javalin.Javalin;

import java.util.Arrays;
import java.util.List;

public class RankProvider {

    Javalin api;

    public RankProvider() {
        api = Javalin.create();
        api.post("/api/all-ranks", ctx -> {
            List<String> ranks = Arrays.stream(PlayerRank.values())
                    .map(Enum::name)
                    .map(String::toLowerCase)
                    .toList();
            ctx.json(ranks);
        });

        api.get("/api/get-all-ranks", ctx -> {
            ctx.result(PlayerRank.listRanks());
        });

        // Optional health route
        api.get("/", ctx -> ctx.result("Rank API is running"));

        api.start(5005);
        System.out.println("✅ Javalin API started on port 5005");
    }
}
