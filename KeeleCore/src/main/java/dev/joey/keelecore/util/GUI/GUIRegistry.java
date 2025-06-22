package dev.joey.keelecore.util.GUI;

import java.util.HashMap;
import java.util.Map;

public class GUIRegistry {

        protected static final Map<String, GUI> registry = new HashMap<>();

        protected static void register(GUI gui) {
            registry.put(gui.usageTag(), gui);
        }

        protected static GUI getGUI(String type) {
            return registry.get(type);
        }
    }
