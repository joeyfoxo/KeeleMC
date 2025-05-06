package dev.joey.keelesurvival.server.events.enderdragon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class ControlDragonSpawn {

    static int dragonSpawnSecondTimer = keeleSurvival.getConfig().getInt("dragonSpawnSeconds");
    public static int secondsElapsed = keeleSurvival.getConfig().getInt("dragonProgressSeconds");

    public ControlDragonSpawn() {

        Bukkit.getScheduler().runTaskTimer(keeleSurvival, () -> {

            if (secondsElapsed < dragonSpawnSecondTimer - 1) {
                secondsElapsed++;
            } else {
                spawnDragon(Bukkit.getWorlds().get(2));
                secondsElapsed = 0;
            }

        }, 0, 20);

    }


    private void spawnDragon(World world) {
        DragonBattle battle = world.getEnderDragonBattle();

        if (!battle.hasBeenPreviouslyKilled()) {
            return;
        }

        if (battle.getEnderDragon() != null) {
            EnderDragon dragon = battle.getEnderDragon();
            dragon.setHealth(0);
        }


        for (Entity entity : world.getEntities()) {
            if (entity instanceof EnderCrystal) {
                entity.remove();
            }

        }

        Bukkit.getScheduler().runTaskLater(keeleSurvival, () -> {

            int portalX = battle.getEndPortalLocation().getBlockX();
            int portalY = battle.getEndPortalLocation().getBlockY() + 1;
            int portalZ = battle.getEndPortalLocation().getBlockZ();
            world.spawnEntity(new Location(world, portalX + 3.4, portalY, portalZ + 0.5), EntityType.END_CRYSTAL).setInvulnerable(true);
            world.spawnEntity(new Location(world, portalX - 2.4, portalY, portalZ + 0.5), EntityType.END_CRYSTAL).setInvulnerable(true);
            world.spawnEntity(new Location(world, portalX + 0.5, portalY, portalZ + 3.4), EntityType.END_CRYSTAL).setInvulnerable(true);
            world.spawnEntity(new Location(world, portalX + 0.5, portalY, portalZ - 2.4), EntityType.END_CRYSTAL).setInvulnerable(true);

            battle.initiateRespawn();

            Bukkit.broadcast(Component.text().content("The Ender Dragon has respawned, slay it to get valuable loot and buffs")
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.UNDERLINED, true)
                    .color(TextColor.color(174, 51, 191)).build());

        }, 220);
    }

}

