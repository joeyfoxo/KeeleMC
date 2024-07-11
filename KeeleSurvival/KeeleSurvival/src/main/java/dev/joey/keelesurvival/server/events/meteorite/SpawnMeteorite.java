package dev.joey.keelesurvival.server.events.meteorite;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import dev.joey.keelesurvival.managers.hook.GriefPreventionHook;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class SpawnMeteorite {

    double percentChance = keeleSurvival.getConfig().getDouble("meteorite_chance");
    int attempts = 0;
    int distanceFromMeteorite = 10000;
    World world = Bukkit.getWorlds().get(0);

    BlockState obsidian = BukkitAdapter.adapt(Material.OBSIDIAN.createBlockData());
    BlockState cobblestone = BukkitAdapter.adapt(Material.COBBLESTONE.createBlockData());
    BlockState deepslate = BukkitAdapter.adapt(Material.DEEPSLATE.createBlockData());
    BlockState stone = BukkitAdapter.adapt(Material.STONE.createBlockData());
    BlockState lava = BukkitAdapter.adapt(Material.LAVA.createBlockData());
    BlockState diamond_ore = BukkitAdapter.adapt(Material.DIAMOND_ORE.createBlockData());
    BlockState netherite_ore = BukkitAdapter.adapt(Material.ANCIENT_DEBRIS.createBlockData());
    BlockState gold_ore = BukkitAdapter.adapt(Material.GOLD_ORE.createBlockData());
    BlockState lapis_ore = BukkitAdapter.adapt(Material.LAPIS_ORE.createBlockData());
    BlockState emerald_ore = BukkitAdapter.adapt(Material.LAPIS_ORE.createBlockData());
    BlockState redstone_ore = BukkitAdapter.adapt(Material.REDSTONE_ORE.createBlockData());
    BlockState copper_ore = BukkitAdapter.adapt(Material.COPPER_ORE.createBlockData());
    BlockState iron_ore = BukkitAdapter.adapt(Material.IRON_ORE.createBlockData());
    BlockState coal_ore = BukkitAdapter.adapt(Material.COAL_ORE.createBlockData());

    public SpawnMeteorite() {

        Bukkit.getScheduler().runTaskTimer(keeleSurvival, () -> {


            if (world.isThundering()) {
                percentChance *= 2;
            } else {
                percentChance = keeleSurvival.getConfig().getDouble("meteorite_chance");
            }

            if (UtilClass.percentageChance(percentChance)) {
                summonMeteorite(world);
            }

        }, 0, 288000);

    }


    private void summonMeteorite(World world) {

        if (attempts >= 20) {
            attempts = 0;
            return;
        }

        int randomX = ThreadLocalRandom.current().nextInt(0, (int) world.getWorldBorder().getSize() / 2);
        int randomZ = ThreadLocalRandom.current().nextInt(0, (int) world.getWorldBorder().getSize() / 2);

        if (world.getNearbyPlayers(new Location(world, randomX, 100, randomZ), distanceFromMeteorite).isEmpty()) {
            attempts++;
            summonMeteorite(world);
            return;
            //When failed spawn no lag at all
        }

        CompletableFuture<Chunk> completableFutureChunk = world.getChunkAtAsync(randomX >> 4, randomZ >> 4);

        completableFutureChunk.thenAccept(chunk -> {

            if (GriefPreventionHook.hasClaimInChunk(chunk)) {
                completableFutureChunk.cancel(true);
                return;
            }

            try (EditSession session = WorldEdit.getInstance().newEditSession(new BukkitWorld(world))) {
                RandomPattern pattern = new RandomPattern();
                RandomPattern orepattern = new RandomPattern();

                int y = world.getHighestBlockYAt(randomX, randomZ);
                int spawnY = ThreadLocalRandom.current().nextInt(20, 25);

                session.makeSphere(BlockVector3.at(randomX, y, randomZ), BukkitAdapter.adapt(Material.AIR.createBlockData()),
                        ThreadLocalRandom.current().nextInt(10, 15), spawnY,
                        ThreadLocalRandom.current().nextInt(10, 15), true);

                pattern.add(cobblestone, 0.5);
                pattern.add(deepslate, 0.6);
                pattern.add(stone, 0.2);
                pattern.add(obsidian, 0.6);
                pattern.add(lava, 0.6);
                orepattern.add(coal_ore, 0.45);
                orepattern.add(iron_ore, 0.4);
                orepattern.add(copper_ore, 0.3);
                orepattern.add(gold_ore, 0.2);
                orepattern.add(redstone_ore, 0.12);
                orepattern.add(emerald_ore, 0.1);
                orepattern.add(diamond_ore, 0.05);
                orepattern.add(lapis_ore, 0.12);
                orepattern.add(netherite_ore, 0.01);

                y = world.getHighestBlockYAt(randomX, randomZ);

                session.makeSphere(BlockVector3.at(randomX, y - spawnY, randomZ), pattern,
                        ThreadLocalRandom.current().nextInt(5, 7),
                        5, ThreadLocalRandom.current().nextInt(5, 7),
                        false);
                session.makeSphere(BlockVector3.at(randomX, y - spawnY, randomZ), orepattern, 4, true);

                int finalY = y;
                Bukkit.getScheduler().runTaskLater(keeleSurvival, () -> session.makeSphere(BlockVector3.at(randomX, finalY - spawnY, randomZ), BukkitAdapter.adapt(Material.STONE.createBlockData()),
                        4, true), 36000);

            }

        });

        world.sendMessage(Component.text()
                .content("A meteorite has landed at X: " + randomX + " Z: " + randomZ)
                .decoration(TextDecoration.BOLD, true)
                .color(TextColor.color(UtilClass.information)));

        attempts = 0;

    }


}
