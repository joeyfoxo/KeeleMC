package dev.joeyfoxo.keelehub;

import dev.joeyfoxo.keelehub.hubselector.HubSelectorGUI;
import dev.joeyfoxo.keelehub.hubselector.HubSelectorItem;
import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.HungerCheck;
import dev.joeyfoxo.keelehub.player.Interactions;
import dev.joeyfoxo.keelehub.player.JoinAndLeaveEvents;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ListenerManager {

    public ListenerManager() {

        HubSelectorItem selectorItem = new HubSelectorItem();

        new JoinAndLeaveEvents(selectorItem.getHubSelector());
        new HubSelectorGUI(selectorItem.getHubSelector());
        new DoubleJump();
        new HungerCheck();
        new Interactions();

    }
}
