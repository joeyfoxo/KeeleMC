package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class SellableItems {

    static Map<Material, Double> sellableItems = new HashMap<>();

    public static void initSellingItems() {
        sellableItems.put(Material.STONE, 1.0);
                sellableItems.put(Material.GRANITE, 1.0);
                sellableItems.put(Material.DIORITE, 1.0);
                sellableItems.put(Material.ANDESITE, 1.0);
                sellableItems.put(Material.GRASS_BLOCK, 1.0);
                sellableItems.put(Material.COBBLESTONE,  0.5);
                sellableItems.put(Material.DIRT, 0.5);
                sellableItems.put(Material.COARSE_DIRT, 1.0);
                sellableItems.put(Material.ROOTED_DIRT, 1.0);
                sellableItems.put(Material.PODZOL, 1.0);
                sellableItems.put(Material.ACACIA_PLANKS, 2.25);
                sellableItems.put(Material.BIRCH_PLANKS, 2.25);
                sellableItems.put(Material.CRIMSON_PLANKS, 2.25);
                sellableItems.put(Material.JUNGLE_PLANKS, 2.25);
                sellableItems.put(Material.OAK_PLANKS, 2.25);
                sellableItems.put(Material.DARK_OAK_PLANKS, 2.25);
                sellableItems.put(Material.MANGROVE_PLANKS, 2.25);
                sellableItems.put(Material.SPRUCE_PLANKS, 2.25);
                sellableItems.put(Material.WARPED_PLANKS, 2.25);
                sellableItems.put(Material.SAND, 0.5);
                sellableItems.put(Material.SANDSTONE, 1.0);
                sellableItems.put(Material.RED_SAND, 1.0);
                sellableItems.put(Material.SOUL_SAND, 1.5);
                sellableItems.put(Material.ACACIA_LOG, 2.25);
                sellableItems.put(Material.BIRCH_LOG, 2.25);
                sellableItems.put(Material.JUNGLE_LOG, 2.25);
                sellableItems.put(Material.DARK_OAK_LOG, 2.25);
                sellableItems.put(Material.MANGROVE_LOG, 2.25);
                sellableItems.put(Material.OAK_LOG, 2.25);
                sellableItems.put(Material.SPRUCE_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_ACACIA_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_BIRCH_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_DARK_OAK_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_JUNGLE_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_MANGROVE_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_OAK_LOG, 2.25);
                sellableItems.put(Material.STRIPPED_SPRUCE_LOG, 2.25);
                sellableItems.put(Material.ACACIA_WOOD, 2.25);
                sellableItems.put(Material.BIRCH_WOOD, 2.25);
                sellableItems.put(Material.JUNGLE_WOOD, 2.25);
                sellableItems.put(Material.DARK_OAK_WOOD, 2.25);
                sellableItems.put(Material.MANGROVE_WOOD, 2.25);
                sellableItems.put(Material.OAK_WOOD, 2.25);
                sellableItems.put(Material.SPRUCE_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_ACACIA_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_BIRCH_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_DARK_OAK_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_JUNGLE_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_MANGROVE_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_OAK_WOOD, 2.25);
                sellableItems.put(Material.STRIPPED_SPRUCE_WOOD, 2.25);
                sellableItems.put(Material.SPONGE, 20.0);
                sellableItems.put(Material.WET_SPONGE, 10.0);
                sellableItems.put(Material.BLACK_WOOL, 4.0);
                sellableItems.put(Material.BLUE_WOOL, 4.0);
                sellableItems.put(Material.BROWN_WOOL, 4.0);
                sellableItems.put(Material.WHITE_WOOL, 4.0);
                sellableItems.put(Material.GRAY_WOOL, 4.0);
                sellableItems.put(Material.CYAN_WOOL, 4.0);
                sellableItems.put(Material.GREEN_WOOL, 4.0);
                sellableItems.put(Material.LIGHT_BLUE_WOOL, 4.0);
                sellableItems.put(Material.LIGHT_GRAY_WOOL, 4.0);
                sellableItems.put(Material.LIME_WOOL, 4.0);
                sellableItems.put(Material.MAGENTA_WOOL, 4.0);
                sellableItems.put(Material.ORANGE_WOOL, 4.0);
                sellableItems.put(Material.PINK_WOOL, 4.0);
                sellableItems.put(Material.PURPLE_WOOL, 4.0);
                sellableItems.put(Material.RED_WOOL, 4.0);
                sellableItems.put(Material.YELLOW_WOOL, 4.0);
                sellableItems.put(Material.OBSIDIAN, 10.0);
                sellableItems.put(Material.CHORUS_FLOWER, 30.0);
                sellableItems.put(Material.FLOWERING_AZALEA, 40.0);
                sellableItems.put(Material.COAL, 5.0);
                sellableItems.put(Material.COAL_BLOCK, 50.0);
                sellableItems.put(Material.IRON_INGOT, 11.0);
                sellableItems.put(Material.COPPER_INGOT, 11.0);
                sellableItems.put(Material.IRON_BLOCK, 100.0);
                sellableItems.put(Material.COPPER_BLOCK, 100.0);
                sellableItems.put(Material.GOLD_INGOT, 20.0);
                sellableItems.put(Material.GOLD_BLOCK, 200.0);
                sellableItems.put(Material.LAPIS_LAZULI, 10.0);
                sellableItems.put(Material.REDSTONE, 10.0);
                sellableItems.put(Material.LAPIS_BLOCK, 95.0);
                sellableItems.put(Material.REDSTONE_BLOCK, 95.5);
                sellableItems.put(Material.DIAMOND, 30.0);
                sellableItems.put(Material.DIAMOND_BLOCK, 300.0);
                sellableItems.put(Material.NETHERITE_SCRAP, 50.0);
                sellableItems.put(Material.NETHERITE_INGOT, 70.0);
                sellableItems.put(Material.NETHERITE_BLOCK, 700.0);
                sellableItems.put(Material.APPLE, 2.5);
                sellableItems.put(Material.CARROT, 2.5);
                sellableItems.put(Material.ENCHANTED_GOLDEN_APPLE, 2000.0);
                sellableItems.put(Material.GOLDEN_APPLE, 200.0);
    }
    public static boolean isSellable(Material material) {
        return sellableItems.containsKey(material);
    }

    public static double getSellPrice(Material material) {
        return sellableItems.get(material);
    }

    public static void sellItem(Material material, Player player) {

        double priceSold;

        if (player.getInventory().getItemInMainHand().getAmount() == 1) {
            KeeleSurvival.getEconomy().depositPlayer(player, getSellPrice(material));
            priceSold = getSellPrice(material);
        } else {
            priceSold = getSellPrice(material) * player.getInventory().getItemInMainHand().getAmount();
            KeeleSurvival.getEconomy().depositPlayer(player, getSellPrice(material) * player.getInventory().getItemInMainHand().getAmount());
        }
        player.getInventory().setItemInMainHand(null);
        UtilClass.sendPlayerMessage(player, "Sold " + material.name() + " for " + priceSold, UtilClass.success);

    }


}
