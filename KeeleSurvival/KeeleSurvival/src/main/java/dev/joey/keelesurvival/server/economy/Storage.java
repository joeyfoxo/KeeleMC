package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.util.ConfigFileHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class Storage {

    public static String getPrefix() {
        return keeleSurvival.getConfig().get("currency").toString();
    }

    private static final HashMap<UUID, Double> playerBalance = new HashMap<>();

    public static HashMap<UUID, Double> getPlayerBalance() {
        return Storage.playerBalance;
    }

    public static void loadBalanceData() {

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        if (configFileHandler.getPlayerFile().getConfigurationSection("player.balance") == null) {
            return;
        }
        configFileHandler.getPlayerFile().getConfigurationSection("player.balance").getKeys(false).stream().toList()
                .forEach(player -> {
                    getPlayerBalance().put(UUID.fromString(player), configFileHandler.getPlayerFile().getDouble("player.balance." + player));

                });
    }

    public static boolean isValidAmount(String amount) {
        return amount.matches("^\\d*(\\.\\d+)?$");
    }

    public static void checkAndCreateAccount(Player player) {

        if (!getEconomy().hasAccount(player)) {
            getEconomy().createPlayerAccount(player);
        }

    }


}

