package dev.joey.keelecore.admin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        for (String rule : getRules()) {
            player.sendMessage(Component.text()
                    .content(ChatColor.translateAlternateColorCodes('&', rule)).build());
        }


        return false;
    }

    private List<String> getRules() {
        return keeleCore.getConfig().getStringList("rules");
    }
}
