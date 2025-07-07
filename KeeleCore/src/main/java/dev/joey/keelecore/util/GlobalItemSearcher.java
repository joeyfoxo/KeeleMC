package dev.joey.keelecore.util;

import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import io.github.ensgijs.nbt.io.BinaryNbtHelpers;
import io.github.ensgijs.nbt.io.CompressionType;
import io.github.ensgijs.nbt.io.NamedTag;
import io.github.ensgijs.nbt.io.TextNbtHelpers;
import io.github.ensgijs.nbt.mca.McaFileBase;
import io.github.ensgijs.nbt.mca.McaPoiFile;
import io.github.ensgijs.nbt.mca.McaRegionFile;
import io.github.ensgijs.nbt.mca.io.McaFileHelpers;
import io.github.ensgijs.nbt.tag.CompoundTag;
import io.github.ensgijs.nbt.tag.ListTag;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
                for (BlockState tileEntity : chunk.getTileEntities()) {
                    if (!(tileEntity instanceof Container container)) continue;

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
                    debug("[NBTScanner] No playerdata folder in world: " + world.getName());
                    continue;
                }

                File[] files = playerDataFolder.listFiles((dir, name) -> name.endsWith(".dat"));
                if (files == null) continue;

                for (File file : files) {
                    String uuidString = file.getName().substring(0, file.getName().length() - 4);
                    UUID uuid = UUID.fromString(uuidString);

                    if (playersChecked.contains(uuid)) {
                        debug("[NBTScanner] Skipping already-checked player: " + uuidString);
                        continue;
                    }

                    debug("[NBTScanner] Reading offline player file: " + file.getName());

                    if (file.length() == 0) {
                        Bukkit.getLogger().warning("[NBTScanner] Player file " + file.getName() + " is empty (0 bytes), skipping.");
                        continue;
                    }

                    try {
                        NamedTag tag = BinaryNbtHelpers.read(file, CompressionType.GZIP); // You may be using Querz's NBT
                        CompoundTag root = (CompoundTag) tag.getTag();

                        if (root == null) {
                            Bukkit.getLogger().warning("[NBTScanner] Player file " + file.getName() + " has null root tag.");
                            continue;
                        }

                        if (!root.containsKey("Inventory")) {
                            Bukkit.getLogger().warning("[NBTScanner] Player file " + file.getName() + " missing 'Inventory' tag, skipping.");
                            continue;
                        }

                        //TODO: Check this item checker

                        ListTag<CompoundTag> inventory = root.getListTag("Inventory").asCompoundTagList();
                        for (CompoundTag item : inventory) {
                            String id = item.getString("id");
                            if (id.equalsIgnoreCase(namespaceID)) {
                                Bukkit.getLogger().info("[NBTScanner] Found " + id + " in player " + uuidString);
                                matches.add(target.clone());
                                break;
                            }
                        }

                        playersChecked.add(uuid);

                    } catch (Exception e) {
                        Bukkit.getLogger().warning("[NBTScanner] Failed to read player file " + file.getName() + ": " + e.getMessage());

                        // Extra debug: try read raw contents and dump beginning of file
                        try {
                            List<String> lines = Files.readAllLines(file.toPath());
                            Bukkit.getLogger().warning("[NBTScanner] Raw content preview of " + file.getName() + ":");
                            for (int i = 0; i < Math.min(lines.size(), 5); i++) {
                                Bukkit.getLogger().warning("  " + lines.get(i));
                            }
                        } catch (IOException ioEx) {
                            Bukkit.getLogger().warning("[NBTScanner] Also failed to read raw file contents: " + ioEx.getMessage());
                        }
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

            File worldDir = new File(Bukkit.getWorldContainer(), "hub"); // root world folder
            List<File> regionDirs = new ArrayList<>();

            // Check typical folders: overworld, nether, the end
            regionDirs.add(new File(worldDir, "region")); // overworld
            regionDirs.add(new File(worldDir, "DIM-1/entities")); // nether
            regionDirs.add(new File(worldDir, "DIM1/region")); // the end

            // Log what we're scanning
            debug("[NBTScanner] Scanning region folders for: " + namespaceID);

            for (File regionFolder : regionDirs) {
                if (!regionFolder.exists()) {
                    debug("[NBTScanner] Region folder does not exist: " + regionFolder.getAbsolutePath());
                    continue;
                }

                File[] regionFiles = regionFolder.listFiles((dir, name) -> name.endsWith(".mca"));
                if (regionFiles == null || regionFiles.length == 0) {
                    debug("[NBTScanner] No .mca files in: " + regionFolder.getAbsolutePath());
                    continue;
                }

                debug("[NBTScanner] Found " + regionFiles.length + " .mca files in: " + regionFolder.getAbsolutePath());

                for (File file : regionFiles) {
                    McaFileBase<?> mcaFile;
                    try {
                        mcaFile = McaFileHelpers.autoMCAFile(file);
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("[NBTScanner] Error loading MCA file: " + file.getName() + ": " + e.getMessage());
                        continue;
                    }

                    if (!(mcaFile instanceof McaRegionFile regionFile)) {
                        debug("[NBTScanner] Skipped non-region MCA file: " + file.getName());
                        continue;
                    }

                    regionFile.forEach(chunk -> {
                        ListTag<CompoundTag> tileEntities = chunk.getTileEntities();
                        if (tileEntities == null) return;

                        for (CompoundTag tileEntity : tileEntities) {
                            if (!tileEntity.containsKey("Items")) continue;

                            String blockEntityID = tileEntity.getString("id");
                            ListTag<CompoundTag> items;
                            try {
                                items = tileEntity.getListTag("Items").asCompoundTagList();
                            } catch (Exception ex) {
                                debug("[NBTScanner] Could not parse Items for " + blockEntityID + " in " + file.getName() + ": " + ex.getMessage());
                                continue;
                            }

                            int x = tileEntity.getInt("x");
                            int y = tileEntity.getInt("y");
                            int z = tileEntity.getInt("z");

                            for (CompoundTag item : items) {
                                String id = item.getString("id");
                                if (id.equalsIgnoreCase(namespaceID)) {
                                    Bukkit.getLogger().info("[NBTScanner] Found " + id + " in " + blockEntityID
                                            + " at " + x + "," + y + "," + z
                                            + " in " + file.getName());
                                    matches.add(target.clone());
                                }
                            }
                        }
                    });
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