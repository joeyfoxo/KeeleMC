package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.bounties.Bounty;
import dev.joey.keelesurvival.server.SurvivalDifficulty;
import dev.joey.keelesurvival.server.economy.SellableItems;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.server.events.enderdragon.ControlDragonSpawn;
import dev.joey.keelesurvival.server.events.headdrop.HeadEvent;

import java.nio.channels.SelectionKey;

public class DataManager {

    public DataManager() {

        Storage.loadBalanceData();
        Bounty.loadBountyData();
        HeadEvent.loadKillData();
        SellableItems.initSellingItems();
        new SurvivalDifficulty();
        new ControlDragonSpawn();

    }
}
