package dev.joey.keelecore.managers;

import dev.joey.keelecore.admin.commands.*;
import dev.joey.keelecore.admin.commands.VanishCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class CommandManager {

    public CommandManager() {

        GameModeCommand gameModeCommand = new GameModeCommand();
        keeleCore.getCommand("gamemode").setExecutor(gameModeCommand);
        keeleCore.getCommand("gamemode").setTabCompleter(gameModeCommand);

        TimeSettingsCommand timeSettingsCommand = new TimeSettingsCommand();
        keeleCore.getCommand("time").setExecutor(timeSettingsCommand);
        keeleCore.getCommand("time").setTabCompleter(timeSettingsCommand);

        TeleportCommand teleportCommand = new TeleportCommand();
        keeleCore.getCommand("teleport").setExecutor(teleportCommand);
        keeleCore.getCommand("teleport").setTabCompleter(teleportCommand);

        VanishCommand vanishCommand = new VanishCommand();
        keeleCore.getCommand("vanish").setExecutor(vanishCommand);
        keeleCore.getCommand("vanish").setTabCompleter(vanishCommand);

        WhoISCommand whoISCommand = new WhoISCommand();
        keeleCore.getCommand("whois").setExecutor(whoISCommand);
        keeleCore.getCommand("whois").setTabCompleter(whoISCommand);

        GodCommand godCommand = new GodCommand();
        keeleCore.getCommand("god").setExecutor(godCommand);
        keeleCore.getCommand("god").setTabCompleter(godCommand);

        FeedCommand feedCommand = new FeedCommand();
        keeleCore.getCommand("feed").setExecutor(feedCommand);
        keeleCore.getCommand("feed").setTabCompleter(feedCommand);

        HealCommand healCommand = new HealCommand();
        keeleCore.getCommand("heal").setExecutor(healCommand);
        keeleCore.getCommand("heal").setTabCompleter(healCommand);

        AnnounceCommand announceCommand = new AnnounceCommand();
        keeleCore.getCommand("announce").setExecutor(announceCommand);
        keeleCore.getCommand("announce").setTabCompleter(announceCommand);

        SpeedCommand speedCommand = new SpeedCommand();
        keeleCore.getCommand("speed").setExecutor(speedCommand);
        keeleCore.getCommand("speed").setTabCompleter(speedCommand);

        SetSpawnCommand setSpawnCommand = new SetSpawnCommand();
        keeleCore.getCommand("setspawn").setExecutor(setSpawnCommand);
        keeleCore.getCommand("setspawn").setTabCompleter(setSpawnCommand);

        SpawnCommand spawnCommand = new SpawnCommand();
        keeleCore.getCommand("spawn").setExecutor(spawnCommand);
        keeleCore.getCommand("spawn").setTabCompleter(spawnCommand);

        HubCommand hubCommand = new HubCommand();
        keeleCore.getCommand("hub").setExecutor(hubCommand);

        DiscordCommand discordCommand = new DiscordCommand();
        keeleCore.getCommand("discord").setExecutor(discordCommand);
        keeleCore.getCommand("discord").setTabCompleter(discordCommand);

        RulesCommand rulesCommand = new RulesCommand();
        keeleCore.getCommand("rules").setExecutor(rulesCommand);

        RankCommand rankCommand = new RankCommand();
        keeleCore.getCommand("rank").setExecutor(rankCommand);
        keeleCore.getCommand("rank").setTabCompleter(rankCommand);

        GalaxyCommand galaxyCommand = new GalaxyCommand();
        keeleCore.getCommand("galaxy").setExecutor(galaxyCommand);
        keeleCore.getCommand("galaxy").setTabCompleter(galaxyCommand);

        WhatAmICommand whatAmICommand = new WhatAmICommand();
        keeleCore.getCommand("whatami").setExecutor(whatAmICommand);
        keeleCore.getCommand("whatami").setTabCompleter(whatAmICommand);

        BanCommand banCommand = new BanCommand();
        keeleCore.getCommand("ban").setExecutor(banCommand);
        keeleCore.getCommand("ban").setTabCompleter(banCommand);

        UnbanCommand unbanCommand = new UnbanCommand();
        keeleCore.getCommand("unban").setExecutor(unbanCommand);
        keeleCore.getCommand("unban").setTabCompleter(unbanCommand);

        BanlistCommand banlistCommand = new BanlistCommand();
        keeleCore.getCommand("banlist").setExecutor(banlistCommand);
        keeleCore.getCommand("banlist").setTabCompleter(banlistCommand);
    }
}
