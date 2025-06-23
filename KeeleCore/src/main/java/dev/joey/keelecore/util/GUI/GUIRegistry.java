package dev.joey.keelecore.util.GUI;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class GUIRegistry {

    // Factory-based registry
    private static final Map<String, Function<Player, GUI>> registry = new HashMap<>();

    public static void register(String tag, Function<Player, GUI> factory) {
        registry.put(tag, factory);
    }

    public static GUI getGUI(String tag, Player player) {
        Function<Player, GUI> factory = registry.get(tag);
        return factory != null ? factory.apply(player) : null;
    }

    public static Set<GUI> getAllGUIsAsSet(Player player) {
        Set<GUI> guis = new HashSet<>();
        for (Function<Player, GUI> factory : registry.values()) {
            guis.add(factory.apply(player));
        }
        return guis;
    }
}