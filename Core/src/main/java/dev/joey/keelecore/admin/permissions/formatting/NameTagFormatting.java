package dev.joey.keelecore.admin.permissions.formatting;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTagFormatting {

    public static void updateNameTag(Player player) {
        // Safe retrieval using Player object
        KeelePlayer kp = PermissionManager.get(player.getUniqueId());
        if (kp == null) {
            player.sendMessage("§cFailed to load your player rank.");
            return;
        }

        PlayerRank rank = kp.getRank();
        String teamName = "rank_" + rank.name().toLowerCase();

        // Use per-player scoreboard to avoid interfering with chat/tab
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        team.prefix(rank.getPrefix()); // This is a Component
        team.addEntry(player.getName());

        player.setScoreboard(scoreboard);
    }
}
