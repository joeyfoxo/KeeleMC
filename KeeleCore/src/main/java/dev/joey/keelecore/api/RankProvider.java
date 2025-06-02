package dev.joey.keelecore.api;

import dev.joey.keelecore.managers.PermissionManager;
import io.javalin.Javalin;

public class RankProvider {

    private final Javalin api;

    public RankProvider() {
        this.api = Javalin.create();

        registerRoutes();

        api.start(5005);
        System.out.println("✅ Javalin API started on port 5005");
    }

    private void registerRoutes() {
        api.get("/api/get-all-ranks", APIRankHandler::getAllRanks);
        api.get("/api/get-player-rank/{uuid}", APIRankHandler::getPlayerRank);
        api.get("/", APIRankHandler::healthCheck);
    }
}