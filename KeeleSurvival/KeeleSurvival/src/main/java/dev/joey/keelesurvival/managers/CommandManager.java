package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.bounties.BountyCommand;
import dev.joey.keelesurvival.server.chestprotection.commands.ChestLockingCommand;
import dev.joey.keelesurvival.server.chestprotection.commands.ChestUnlockingCommand;
import dev.joey.keelesurvival.server.chestprotection.commands.TrustAccessCommand;
import dev.joey.keelesurvival.server.economy.commands.*;
import dev.joey.keelesurvival.server.events.PlayerFirstTimeBook;
import dev.joey.keelesurvival.server.wildtp.WildTPCommand;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class CommandManager {

    public CommandManager() {

        keeleSurvival.getCommand("pay").setExecutor(new PayCommand());
        keeleSurvival.getCommand("balance").setExecutor(new BalanceCommand());
        keeleSurvival.getCommand("eco").setExecutor(new EcoCommand());
        keeleSurvival.getCommand("unlock").setExecutor(new ChestUnlockingCommand());
        keeleSurvival.getCommand("lock").setExecutor(new ChestLockingCommand());
        keeleSurvival.getCommand("access").setExecutor(new TrustAccessCommand());
        keeleSurvival.getCommand("wild").setExecutor(new WildTPCommand());
        keeleSurvival.getCommand("bounty").setExecutor(new BountyCommand());
        keeleSurvival.getCommand("book").setExecutor(new PlayerFirstTimeBook());
        keeleSurvival.getCommand("sell").setExecutor(new SellCommand());
        keeleSurvival.getCommand("withdraw").setExecutor(new WithdrawCommand());

    }
}
