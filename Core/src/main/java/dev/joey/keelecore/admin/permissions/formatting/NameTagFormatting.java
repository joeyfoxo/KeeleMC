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
        KeelePlayer keelePlayer = PermissionManager.getCached(player.getUniqueId());

        if (keelePlayer == null) {
            player.sendMessage("§cFailed to load your player rank.");
            return;
        }

        PlayerRank rank = keelePlayer.getRank();
        String teamName = "rank_" + rank.name().toLowerCase();

        // Use per-player scoreboard to avoid interfering with other teams
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        team.prefix(rank.getPrefix()); // Adventure Component API
        team.addEntry(player.getName());

        player.setScoreboard(scoreboard);
    }
}