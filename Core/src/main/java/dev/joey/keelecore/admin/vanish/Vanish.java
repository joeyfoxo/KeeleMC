package dev.joey.keelecore.admin.vanish;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.ConfigFileHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class Vanish extends SuperCommand {

    protected boolean hasPermissionToSee(KeelePlayer keelePlayer) {
        return keelePlayer.getRank().isStaff();
    }

}
