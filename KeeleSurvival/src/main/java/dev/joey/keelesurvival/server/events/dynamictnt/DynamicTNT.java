package dev.joey.keelesurvival.server.events.dynamictnt;

import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class DynamicTNT implements Listener {

    public static ArrayList<FallingBlock> flyingBlocks = new ArrayList<>();

    public DynamicTNT() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (block.getType() != Material.TNT) {
                FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());

                throwBlocks(fallingBlock);
            }
        }



    }

    public static void throwBlocks(FallingBlock fallingBlock) {

        if (UtilClass.percentageChance(0.10D)) {
            double x = ThreadLocalRandom.current().nextDouble(-5, 15);
            double y = ThreadLocalRandom.current().nextDouble(2, 20);
            double z = ThreadLocalRandom.current().nextDouble(-5, 15);
            fallingBlock.setVelocity(new Vector(x, y, z).normalize());
            flyingBlocks.add(fallingBlock);
        } else {
            fallingBlock.setDropItem(false);
        }
    }
}
