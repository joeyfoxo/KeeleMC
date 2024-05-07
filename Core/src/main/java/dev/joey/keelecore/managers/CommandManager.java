package dev.joey.keelecore.managers;

import dev.joey.keelecore.admin.commands.*;
import dev.joey.keelecore.admin.vanish.VanishCommand;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class CommandManager {

    public CommandManager() {

        keeleCore.getCommand("gamemode").setExecutor(new GameModeCommand());
        keeleCore.getCommand("time").setExecutor(new TimeSettingsCommand());
        keeleCore.getCommand("teleport").setExecutor(new TeleportCommand());
        keeleCore.getCommand("vanish").setExecutor(new VanishCommand());
        keeleCore.getCommand("whois").setExecutor(new WhoISCommand());
        keeleCore.getCommand("god").setExecutor(new GodCommand());
        keeleCore.getCommand("feed").setExecutor(new FeedCommand());
        keeleCore.getCommand("heal").setExecutor(new HealCommand());
        keeleCore.getCommand("announce").setExecutor(new AnnounceCommand());
        keeleCore.getCommand("speed").setExecutor(new SpeedCommand());
        keeleCore.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        keeleCore.getCommand("spawn").setExecutor(new SpawnCommand());
        keeleCore.getCommand("hub").setExecutor(new HubCommand());
        keeleCore.getCommand("discord").setExecutor(new DiscordCommand());
        keeleCore.getCommand("rules").setExecutor(new RulesCommand());

    }
}
