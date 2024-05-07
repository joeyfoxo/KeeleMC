package dev.joey.keelesurvival.server.events.headdrop;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.joey.keelesurvival.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class HeadEvent implements Listener {

    public static Map<UUID, Integer> playerKills = new HashMap<>();
    HashSet<UUID> headDropDelay = new HashSet<>();

    public HeadEvent() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (player.getKiller() != null) {
            if (playerKills.containsKey(player.getKiller().getUniqueId())) {
                playerKills.put(player.getKiller().getUniqueId(), playerKills.get(player.getKiller().getUniqueId()) + 1);
            }
            else {
                playerKills.put(player.getKiller().getUniqueId(), 1);
            }
            dropPlayerHeadOnDeath(player.getKiller(), player);
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            dropMobHeadOnDeath(event.getEntity().getKiller(), event.getEntity());
        }
    }

    private void dropPlayerHeadOnDeath(Player killer, Player victim) {

        if (killer != null && UtilClass.percentageChance(0.1D) && killer.getUniqueId() != victim.getUniqueId()) {
            if (headDropDelay.contains(victim.getUniqueId())) {
                UtilClass.sendPlayerMessage(killer, "That player's head drop is on cooldown", UtilClass.error);
                return;
            }
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(victim);
            if (playerKills.containsKey(victim.getUniqueId())) {
                meta.setCustomModelData(playerKills.get(victim.getUniqueId()) * 200);
            }
            else {
                meta.setCustomModelData(200);
            }
            playerKills.remove(victim.getUniqueId());

            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);
            headDropDelay.add(victim.getUniqueId());

            Bukkit.getScheduler().runTaskLater(keeleSurvival, () -> headDropDelay.remove(victim.getUniqueId()), 18000);

        }

    }

    private void dropMobHeadOnDeath(Player killer, LivingEntity victim) {

        if (killer != null && UtilClass.percentageChance(0.05D) && !(victim instanceof Player)) {
            if (victim instanceof Ageable ageable) {
                if (!ageable.isAdult()) {
                    return;
                }
            }

            if (victim instanceof EnderDragon) {
                victim.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(Material.DRAGON_HEAD));
                return;
            }

            HeadDropping headSelector = new HeadDropping(victim);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", headSelector.getEncodedTexture()));
            Field profileField;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
                e1.printStackTrace();
            }

            meta.displayName(Component.text()
                    .content(victim.getName() + " Head")
                    .color(TextColor.color(255, 255, 85))
                    .decoration(TextDecoration.ITALIC, false)
                    .build());
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }

    public static void loadKillData() {

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        if (configFileHandler.getPlayerFile().getConfigurationSection("player.kills") == null) {
            return;
        }
        configFileHandler.getPlayerFile().getConfigurationSection("player.kills").getKeys(false).stream().toList()
                .forEach(player -> playerKills.put(UUID.fromString(player), configFileHandler.getPlayerFile().getInt("player.kills." + player)));
    }

}
