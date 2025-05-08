package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.listeners.BlockDefaultThings;
import dev.joey.keelecore.armour.galaxy.ArmourListener;
import dev.joey.keelecore.auth.listeners.JoinNLeaveListener;
import dev.joey.keelecore.server.restarting.DailyRestart;
import dev.joey.keelecore.admin.permissions.formatting.ChatFormatting;
import org.bukkit.Bukkit;

public class ListenerManager {


    public ListenerManager() {
        new BlockDefaultThings();
        //new GUIPlayerListener();
        new JoinNLeaveListener();
        new ChatFormatting();
        new DailyRestart();
        //new AutoAnnouncer();
        new ArmourListener();
    }
}
