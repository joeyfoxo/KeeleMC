package dev.joeyfoxo.keelehub;

import dev.joeyfoxo.keelehub.hubselector.HubSelectorGUI;
import dev.joeyfoxo.keelehub.hubselector.HubSelectorItem;
import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.HungerCheck;
import dev.joeyfoxo.keelehub.player.Interactions;
import dev.joeyfoxo.keelehub.player.JoinAndLeaveEvents;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class ListenerManager {

    public ListenerManager() {

        ItemStack itemStack = new HubSelectorItem().getHubSelector();

        new HubSelectorGUI(itemStack);
        new JoinAndLeaveEvents(itemStack);
        new DoubleJump();
        new HungerCheck();
        new Interactions();

    }
}
