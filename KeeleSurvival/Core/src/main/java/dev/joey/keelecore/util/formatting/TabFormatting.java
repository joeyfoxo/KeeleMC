package dev.joey.keelecore.util.formatting;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TabFormatting {

    LuckPerms luckPerms;

    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard mainBoard = manager.getMainScoreboard();

    public TabFormatting() {

        if (provider != null) {
            luckPerms = provider.getProvider();
            for (Group group : luckPerms.getGroupManager().getLoadedGroups()) {
                if (group.getCachedData().getMetaData().getPrefix() != null) {
                    Component prefixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(group.getCachedData().getMetaData().getPrefix());
                    mainBoard.registerNewTeam(group.getName()).prefix(prefixComponent);
                    if (group.getName().equals("student")) {
                        if (!KeeleCore.keeleStudent.isEmpty()) {
                            KeeleCore.keeleStudent.forEach(student -> {
                                if (student != null) {
                                    mainBoard.getTeam("student").addPlayer(Bukkit.getOfflinePlayer(UUID.fromString(student)));
                                }

                            });
                        }
                    }


                    if (group.getName().equals("default")) {
                        if (!KeeleCore.nonStudent.isEmpty()) {
                            KeeleCore.nonStudent.forEach(student -> {
                                if (student != null) {
                                    mainBoard.getTeam("default").addPlayer(Bukkit.getOfflinePlayer(UUID.fromString(student)));
                                }
                            });
                        }
                    }
                }
            }
        }
    }


    public void assignTeam(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            // Define the staff groups
            List<String> staffGroups = Arrays.asList("owner", "dev", "admin", "mod");

            // Get the highest ranking staff group
            Optional<Group> highestRankingGroup = user.getNodes().stream()
                    .filter(node -> node instanceof InheritanceNode)
                    .map(node -> ((InheritanceNode) node).getGroupName())
                    .filter(staffGroups::contains)
                    .map(groupName -> luckPerms.getGroupManager().getGroup(groupName))
                    .filter(Objects::nonNull)
                    .max(Comparator.comparingInt(group -> group.getWeight().orElse(0)));

            // If the highest ranking staff group exists, add the player to the corresponding team
            if (highestRankingGroup.isPresent()) {
                Team team = mainBoard.getTeam(highestRankingGroup.get().getName());
                if (team != null && !team.hasEntry(player.getName())) {
                    // Remove player from all other teams
                    for (Team otherTeam : mainBoard.getTeams()) {
                        if (otherTeam.hasEntry(player.getName())) {
                            otherTeam.removeEntry(player.getName());
                        }
                    }

                    String prefixContent = LegacyComponentSerializer.legacyAmpersand().serialize(team.prefix()).trim(); // Get the prefix with color codes and trim it
                    team.prefix(LegacyComponentSerializer.legacyAmpersand().deserialize(prefixContent + " ")); // Add a space at the end of the prefix
                    team.addEntry(player.getName());
                }
            } else {
                // If the user is not a member of any staff group, check if they are a member of the "student" or "default" group
                for (String groupName : Arrays.asList("student", "default")) {
                    if (user.getNodes().stream().anyMatch(node -> node instanceof InheritanceNode && ((InheritanceNode) node).getGroupName().equals(groupName))) {
                        Team team = mainBoard.getTeam(groupName);
                        if (team != null && !team.hasEntry(player.getName())) {
                            // Remove player from all other teams
                            for (Team otherTeam : mainBoard.getTeams()) {
                                if (otherTeam.hasEntry(player.getName())) {
                                    otherTeam.removeEntry(player.getName());
                                }
                            }

                            team.addEntry(player.getName());
                        }
                    }
                }
            }
        }
    }

}
