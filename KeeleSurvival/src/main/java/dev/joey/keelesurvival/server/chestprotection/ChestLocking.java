package dev.joey.keelesurvival.server.chestprotection;

import dev.joey.keelesurvival.managers.hook.GriefPreventionHook;
import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class ChestLocking extends SuperCommand {

    ConfigFileHandler configFileHandler = new ConfigFileHandler();

    static HashMap<Integer, List<String>> lockedChestMap = new HashMap<>();
    static List<Block> chests = new LinkedList<>();


    protected void lockChest(Player player, Block block) {

        if (playerNullCheck(player, player)) return;
        if (block != null && !isLocked(block)) {

            List<String> playersWhoCanAccess = new ArrayList<>(List.of(player.getUniqueId().toString()));
            lockedChestMap.put(block.hashCode(), playersWhoCanAccess);
            configFileHandler.getChestFile().set("chests." + block.hashCode() + ".access", playersWhoCanAccess);
            chests.add(block);
            UtilClass.sendPlayerMessage(player, "Chest Locked", UtilClass.error);
        }
    }

    protected void unlockChest(Player player, Block block) {

        if (playerNullCheck(player, player)) return;
        if (block != null && isLocked(block)) {

            lockedChestMap.remove(block.hashCode());
            configFileHandler.getChestFile().set("chests." + block.hashCode(), null);
            chests.remove(block);
            UtilClass.sendPlayerMessage(player, "Chest Unlocked", UtilClass.success);
        }
    }

    protected List<String> getPlayersWhoCanAccess(Block block) {
        return lockedChestMap.get(block.hashCode());
    }


    protected boolean hasAccess(Player player, Block block) {
        return lockedChestMap.get(block.hashCode()).contains(player.getUniqueId().toString())
                || GriefPreventionHook.hasTrustAccess(player);
    }

    protected boolean isLocked(Block block) {
        return lockedChestMap.containsKey(block.hashCode());
    }

    protected boolean isOwner(Player player, Block block) {
        return lockedChestMap.get(block.hashCode()).get(0).equals(player.getUniqueId().toString());
    }

    protected void loadChestData() {

        if (configFileHandler.getChestFile().getConfigurationSection("chests") == null) {
            return;
        }
        configFileHandler.getChestFile().getConfigurationSection("chests").getKeys(false).stream().toList()
                .forEach(chest -> lockedChestMap.put(Integer.valueOf(chest), configFileHandler.getChestFile().getStringList("chests." + chest + ".access")));
    }

    protected ArrayList<Block> getAdjacentBlocks(Block placedBlock) {


        return new ArrayList<>(
                List.of(placedBlock.getRelative(-1, 0, 0)
                        , placedBlock.getRelative(1, 0, 0)
                        , placedBlock.getRelative(0, 0, -1)
                        , placedBlock.getRelative(0, 0, 1)));
    }

    protected boolean canPlayerPlaceChestAdjacent(Player player, Block placedBlock) {

        for (Block blocks : getAdjacentBlocks(placedBlock)) {
            if (blocks.getType() == Material.CHEST) {
                if (isLocked(blocks)) {
                    return hasAccess(player, blocks);
                }
            }

        }
        return true;
    }

    protected boolean isDoubleChest(Block placedBlock) {

        if (placedBlock.getBlockData() instanceof Chest chest) {
            return chest.getType() == Chest.Type.LEFT || chest.getType() == Chest.Type.RIGHT;
        }
        return false;
    }

    protected void unlockOrLockDoubleChests(Player player, Block block) {

        getAdjacentBlocks(block).forEach(adjacentBlock -> {
            if (adjacentBlock.getType() == Material.CHEST) {
                if (isLocked(adjacentBlock) && !isLocked(block)) {
                    lockChest(player, block);
                    return;
                }
                if (isLocked(adjacentBlock) && isLocked(block)) {
                    unlockChest(player, block);
                    unlockChest(player, adjacentBlock);
                } else {
                    lockChest(player, block);
                    lockChest(player, adjacentBlock);
                }


            }


        });

    }

    protected boolean addTrustedPlayer(Player player, Block block) {

        if (isLocked(block) && getPlayersWhoCanAccess(block).contains(player.getUniqueId().toString())) {
            return false;
        }

        if (isDoubleChest(block)) {

            getAdjacentBlocks(block).forEach(adjacentBlock -> {
                if (adjacentBlock.getType() == Material.CHEST) {
                    if (isLocked(adjacentBlock)) {
                        getPlayersWhoCanAccess(block).add(player.getUniqueId().toString());
                        getPlayersWhoCanAccess(adjacentBlock).add(player.getUniqueId().toString());
                        lockedChestMap.put(block.hashCode(), getPlayersWhoCanAccess(block));
                        lockedChestMap.put(adjacentBlock.hashCode(), getPlayersWhoCanAccess(adjacentBlock));
                    }
                }
            });
        } else {
            getPlayersWhoCanAccess(block).add(player.getUniqueId().toString());
        }
        return true;
    }

    protected boolean removeTrustedPlayer(Player player, Block block) {

        if (isLocked(block) && !getPlayersWhoCanAccess(block).contains(player.getUniqueId().toString())) {
            return false;
        }

        if (isDoubleChest(block)) {

            getAdjacentBlocks(block).forEach(adjacentBlock -> {
                if (adjacentBlock.getType() == Material.CHEST) {
                    if (isLocked(adjacentBlock)) {
                        getPlayersWhoCanAccess(block).remove(player.getUniqueId().toString());
                        getPlayersWhoCanAccess(adjacentBlock).remove(player.getUniqueId().toString());
                        lockedChestMap.put(block.hashCode(), getPlayersWhoCanAccess(block));
                        lockedChestMap.put(adjacentBlock.hashCode(), getPlayersWhoCanAccess(adjacentBlock));
                    }
                }
            });
        } else {
            getPlayersWhoCanAccess(block).remove(player.getUniqueId().toString());
        }
        return true;
    }
}
