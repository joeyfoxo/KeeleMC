package dev.joey.keelecore.admin.permissions.formatting;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTagFormatting {

    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    public static void updateNameTag(Player player, PlayerRank rank) {

        String teamName = rank.name();

        // Use or create the team
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.setPrefix(LegacyComponentSerializer.legacySection().serialize(rank.getPrefix()));
            team.setSuffix(LegacyComponentSerializer.legacySection().serialize(rank.getSuffix()));
        }

        // Remove player from any old team
        for (Team t : scoreboard.getTeams()) {
            t.removeEntry(player.getName());
        }

        team.addEntry(player.getName());
        player.setScoreboard(scoreboard);
    }
}