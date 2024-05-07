package dev.joey.keelesurvival.server.bounties;

import dev.joey.keelesurvival.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class Bounty {

    static HashMap<UUID, Double> playerBounties = new HashMap<>();

    static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    static Team team = scoreboard.registerNewTeam("bounty");

    public static Team getTeam() {
        team.color(NamedTextColor.RED);
        return team;
    }

    static Inventory bountyInventory = Bukkit.createInventory(null, 54, Component.text().content("Most Wanted").color(TextColor.color(UtilClass.information)).build());


    public static HashMap<UUID, Double> getPlayerBounties() {
        return playerBounties;
    }

    public static boolean hasBounty(Player player) {
        return playerBounties.containsKey(player.getUniqueId());
    }

    public static void setBounty(UUID uuid, Double amount) {
        playerBounties.put(uuid, amount);

    }

    public static double getBounty(UUID uuid) {
        return getPlayerBounties().get(uuid);
    }

    public static void removeBounty(UUID uuid) {
        playerBounties.remove(uuid);
        getTeam().removePlayer(Bukkit.getOfflinePlayer(uuid));
    }

    public static void loadBountyData() {

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        if (configFileHandler.getPlayerFile().getConfigurationSection("player.bounties") == null) {
            return;
        }
        configFileHandler.getPlayerFile().getConfigurationSection("player.bounties").getKeys(false).stream().toList()
                .forEach(player -> playerBounties.put(UUID.fromString(player), configFileHandler.getPlayerFile().getDouble("player.bounties." + player)));

        Bukkit.getScheduler().runTaskTimer(keeleSurvival, () -> {

            for (UUID uuid : Bounty.getPlayerBounties().keySet()) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                if (!Bounty.getTeam().hasPlayer(player)) {
                    Bounty.getTeam().addPlayer(player);

                }
            }

        }, 1200, 200);
    }

}
