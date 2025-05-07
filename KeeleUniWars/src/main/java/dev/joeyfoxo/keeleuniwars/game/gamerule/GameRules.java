package dev.joeyfoxo.keeleuniwars.game.gamerule;

import dev.joeyfoxo.core.game.GameStatus;
import dev.joeyfoxo.keeleuniwars.game.WallsGame;
import dev.joeyfoxo.keeleuniwars.game.teams.WallsPlayer;
import dev.joeyfoxo.keeleuniwars.util.Util;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameRules implements Listener {

    WallsGame game;

    public GameRules(WallsGame game, World world) {

        this.game = game;
        setGameRule(world, GameRule.ANNOUNCE_ADVANCEMENTS, false);
        setGameRule(world, GameRule.COMMAND_BLOCK_OUTPUT, false);
        setGameRule(world, GameRule.KEEP_INVENTORY, false);
        setGameRule(world, GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        setGameRule(world, GameRule.DISABLE_RAIDS, true);
        setGameRule(world, GameRule.DO_IMMEDIATE_RESPAWN, false);
        setGameRule(world, GameRule.DO_DAYLIGHT_CYCLE, false);
        setGameRule(world, GameRule.SPAWN_RADIUS, 0);

        Util.keeleUniWars.getServer().getPluginManager().registerEvents(this, Util.keeleUniWars);

    }

    public static void setGameRule(World world, GameRule<Boolean> gameRule, boolean enabled) {
        world.setGameRule(gameRule, enabled);
    }

    public static void setGameRule(World world, GameRule<Integer> gameRule, int enabled) {
        world.setGameRule(gameRule, enabled);
    }

}