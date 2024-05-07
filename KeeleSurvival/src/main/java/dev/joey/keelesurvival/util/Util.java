package dev.joey.keelesurvival.util;

import dev.joey.keelesurvival.KeeleSurvival;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Util {

    public static KeeleSurvival keeleSurvival;
    public static String keeleSurvivalNameSpace = "keelesurvival";


    public static Map<UUID, Double> sortByValue(Map<UUID, Double> unsortedMap, final boolean order) {
        List<Map.Entry<UUID, Double>> list = new java.util.ArrayList<>(unsortedMap.entrySet().stream().toList());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
