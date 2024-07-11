package dev.joey.keelecore.admin.vanish;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.ConfigFileHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class Vanish extends SuperCommand {

    static List<String> vanishedPlayers = new ArrayList<>(new ConfigFileHandler(keeleCore).getPlayerFile().getStringList("vanished"));

    public static List<String> getVanishedPlayers() {
        return vanishedPlayers;
    }

    protected boolean hasPermissionToSee(Player player) {
        return player.isOp() || player.hasPermission("kc.see");
    }

    protected static boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId().toString());
    }

}
