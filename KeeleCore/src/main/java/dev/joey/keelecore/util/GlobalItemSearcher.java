package dev.joey.keelecore.util;

import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import net.querz.mca.MCAFile;
import net.querz.mca.MCAUtil;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalItemSearcher {

    private static final boolean DEBUG = true; // Toggle this for debug logs

    private static HashSet<UUID> playersChecked = new HashSet<>(); // Track players already checked in async scans

    /**
     * Runs a comprehensive scan for the specified item across online players, loaded chunks,
     * offline player data, and unloaded chunk files.
     *
     * <p><b>Warning:</b> This method is heavy and potentially slow. It performs extensive disk
     * IO and large data processing asynchronously. It should be used sparingly, primarily
     * for administrative or maintenance purposes.</p>
     *
     * @param itemStack The ItemStack to search for.
     */
    @ApiStatus.Experimental
    public static void runFullItemScan(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            Bukkit.getLogger().warning("[NBTScanner] Invalid item to scan.");
            return;
        }

        // Scan online players and loaded containers
        debug("[NBTScanner] Starting sync scan for online players and loaded chunks...");
        List<ItemStack> onlineMatches = scanOnlinePlayersAndLoadedChunks(itemStack);
        Bukkit.getLogger().info("[NBTScanner] Found " + onlineMatches.size() + " matches in online players and loaded chunks.");

        // Scan offline players
        debug("[NBTScanner] Scheduling async scan for offline players...");
        scanOfflinePlayersForItem(itemStack).thenAccept(offlineMatches -> {
            Bukkit.getLogger().info("[NBTScanner] Found " + offlineMatches.size() + " matches in offline player data.");
        });

        // Scan unloaded chunks
        debug("[NBTScanner] Scheduling async scan for unloaded chunks...");
        scanUnloadedChunksForItem(itemStack).thenAccept(unloadedMatches -> {
            Bukkit.getLogger().info("[NBTScanner] Found " + unloadedMatches.size() + " matches in unloaded chunks.");
        });
    }

    public static List<ItemStack> scanOnlinePlayersAndLoadedChunks(ItemStack target) {
        List<ItemStack> matches = new CopyOnWriteArrayList<>();

        debug("[NBTScanner] Scanning online players...");
        for (KeelePlayer player : PlayerPermManager.getPlayerCollection()) {
            playersChecked.add(player.getUuid());
            ItemStack[] inventory = player.getPlayer().getInventory().getContents();
            debug("Scanning inventory of " + player.getName() + " (" + inventory.length + " items)");

            for (ItemStack item : inventory) {
                if (item != null && item.isSimilar(target)) {
                    matches.add(item);
                    Bukkit.getLogger().info("[NBTScanner] Found " + item.getType() + " in inventory of " + player.getName());
                }
            }
        }

        debug("[NBTScanner] Scanning loaded chunks...");
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (org.bukkit.block.BlockState tileEntity : chunk.getTileEntities()) {
                    if (!(tileEntity instanceof org.bukkit.block.Container container)) continue;

                    ItemStack[] contents = container.getInventory().getStorageContents();
                    for (ItemStack item : contents) {
                        if (item == null || item.getType().isAir()) continue;
                        if (item.isSimilar(target)) {
                            matches.add(item);
                            Bukkit.getLogger().info("[NBTScanner] Found " + item.getType() + " in container at " + tileEntity.getLocation());
                            break;
                        }
                    }
                }
            }
        }

        return matches;
    }

    public static CompletableFuture<List<ItemStack>> scanOfflinePlayersForItem(ItemStack target) {

        Bukkit.getServer().savePlayers();

        return CompletableFuture.supplyAsync(() -> {
            List<ItemStack> matches = new CopyOnWriteArrayList<>();
            String namespaceID = target.getType().getKey().toString();

            for (World world : Bukkit.getWorlds()) {
                File playerDataFolder = new File(world.getWorldFolder(), "playerdata");
                if (!playerDataFolder.exists()) {
                    debug("No playerdata folder in world: " + world.getName());
                    continue;
                }

                File[] files = playerDataFolder.listFiles((dir, name) -> name.endsWith(".dat"));
                if (files == null) continue;

                for (File file : files) {

                    String uuidString = file.getName().replace(".dat", "");
                    if (playersChecked.contains(UUID.fromString(uuidString))) {
                        continue;
                    }

                    debug("Reading offline player file: " + file.getName());
                    try {
                        NamedTag tag = NBTUtil.read(file);
                        CompoundTag root = (CompoundTag) tag.getTag();

                        // Modern format: no "Level" tag, check root directly
                        if (root == null || !root.containsKey("Inventory")) {
                            Bukkit.getLogger().warning("[NBTScanner] Player file " + file.getName() + " missing 'Inventory' tag, skipping.");
                            continue;
                        }

                        ListTag<CompoundTag> inventory = root.getListTag("Inventory").asCompoundTagList();

                        for (CompoundTag item : inventory) {
                            String id = item.getString("id");
                            if (id.equalsIgnoreCase(namespaceID)) {
                                Bukkit.getLogger().info("[NBTScanner] Found " + id + " in player " + file.getName().replace(".dat", ""));
                                matches.add(target.clone());
                            }
                        }
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("[NBTScanner] Failed to read player file " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
            return matches;
        });
    }

    public static CompletableFuture<List<ItemStack>> scanUnloadedChunksForItem(ItemStack target) {
        return CompletableFuture.supplyAsync(() -> {
            List<ItemStack> matches = new CopyOnWriteArrayList<>();
            String namespaceID = target.getType().getKey().toString();

            for (World world : Bukkit.getWorlds()) {
                File regionFolder = new File(world.getWorldFolder(), "region");
                if (!regionFolder.exists()) {
                    debug("[NBTScanner] No region folder for world: " + world.getName());
                    continue;
                }

                File[] regionFiles = regionFolder.listFiles((dir, name) -> name.endsWith(".mca"));
                if (regionFiles == null) continue;

                for (File file : regionFiles) {
                    try {
                        MCAFile mca = MCAUtil.read(file);

                        debug(mca.toString());

                        for (int cx = 0; cx < 32; cx++) {
                            for (int cz = 0; cz < 32; cz++) {
                                net.querz.mca.Chunk chunk = mca.getChunk(cx, cz);
                                if (chunk == null) continue;

                                debug("[NBTScanner] Scanning chunk [" + cx + "," + cz + "] in " + file.getName());

                                ListTag<CompoundTag> tileEntities;
                                try {
                                    tileEntities = chunk.getTileEntities();
                                } catch (Exception teEx) {
                                    debug("[NBTScanner] Failed to get tile entities from chunk [" + cx + "," + cz + "]: " + teEx.getMessage());
                                    continue;
                                }

                                if (tileEntities == null) continue;

                                for (CompoundTag tileEntity : tileEntities) {
                                    String blockEntityID = tileEntity.getString("id");
                                    if (!tileEntity.containsKey("Items")) continue;

                                    ListTag<CompoundTag> items;
                                    try {
                                        items = tileEntity.getListTag("Items").asCompoundTagList();
                                    } catch (Exception ie) {
                                        debug("[NBTScanner] Failed to read Items tag in " + blockEntityID + ": " + ie.getMessage());
                                        continue;
                                    }

                                    for (CompoundTag item : items) {
                                        String id = item.getString("id");
                                        if (id.equalsIgnoreCase(namespaceID)) {
                                            int x = tileEntity.getInt("x");
                                            int y = tileEntity.getInt("y");
                                            int z = tileEntity.getInt("z");
                                            Bukkit.getLogger().info("[NBTScanner] Found " + id + " in " + blockEntityID + " at " + x + "," + y + "," + z + " in " + file.getName());
                                            matches.add(target.clone());
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
            return matches;
        });
    }

    private static void debug(String msg) {
        if (DEBUG) {
            Bukkit.getLogger().info(msg);
        }
    }
}