package dev.joey.keelecore.managers;

import dev.joey.keelecore.admin.listeners.BlockDefaultThings;
import dev.joey.keelecore.admin.vanish.VanishListener;
import dev.joey.keelecore.auth.listeners.JoinNLeaveListener;
import dev.joey.keelecore.server.restarting.DailyRestart;
import dev.joey.keelecore.admin.permissions.formatting.ChatFormatting;

public class ListenerManager {


    public ListenerManager() {
        new BlockDefaultThings();
        //new GUIPlayerListener();
        new JoinNLeaveListener();
        new ChatFormatting();
        new VanishListener();
        new DailyRestart();
        //new AutoAnnouncer();

    }
}
