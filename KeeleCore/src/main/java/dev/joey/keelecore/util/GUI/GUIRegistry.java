package dev.joey.keelecore.util.GUI;

import java.util.HashMap;
import java.util.Map;

public class GUIRegistry {

        private static final Map<String, GUI> registry = new HashMap<>();

        public static void register(GUI gui) {
            registry.put(gui.usageTag(), gui);
        }

        public static GUI getGUI(String type) {
            return registry.get(type);
        }
    }
