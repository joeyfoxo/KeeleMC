package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.commands.*;


public class CommandManager {

    public CommandManager() {

        GameModeCommand gameModeCommand = new GameModeCommand();
        KeeleCore.getInstance().getCommand("gamemode").setExecutor(gameModeCommand);
        KeeleCore.getInstance().getCommand("gamemode").setTabCompleter(gameModeCommand);

        TimeSettingsCommand timeSettingsCommand = new TimeSettingsCommand();
        KeeleCore.getInstance().getCommand("time").setExecutor(timeSettingsCommand);
        KeeleCore.getInstance().getCommand("time").setTabCompleter(timeSettingsCommand);

        TeleportCommand teleportCommand = new TeleportCommand();
        KeeleCore.getInstance().getCommand("teleport").setExecutor(teleportCommand);
        KeeleCore.getInstance().getCommand("teleport").setTabCompleter(teleportCommand);

        VanishCommand vanishCommand = new VanishCommand();
        KeeleCore.getInstance().getCommand("vanish").setExecutor(vanishCommand);
        KeeleCore.getInstance().getCommand("vanish").setTabCompleter(vanishCommand);

        WhoISCommand whoISCommand = new WhoISCommand();
        KeeleCore.getInstance().getCommand("whois").setExecutor(whoISCommand);
        KeeleCore.getInstance().getCommand("whois").setTabCompleter(whoISCommand);

        GodCommand godCommand = new GodCommand();
        KeeleCore.getInstance().getCommand("god").setExecutor(godCommand);
        KeeleCore.getInstance().getCommand("god").setTabCompleter(godCommand);

        FeedCommand feedCommand = new FeedCommand();
        KeeleCore.getInstance().getCommand("feed").setExecutor(feedCommand);
        KeeleCore.getInstance().getCommand("feed").setTabCompleter(feedCommand);

        HealCommand healCommand = new HealCommand();
        KeeleCore.getInstance().getCommand("heal").setExecutor(healCommand);
        KeeleCore.getInstance().getCommand("heal").setTabCompleter(healCommand);

        AnnounceCommand announceCommand = new AnnounceCommand();
        KeeleCore.getInstance().getCommand("announce").setExecutor(announceCommand);
        KeeleCore.getInstance().getCommand("announce").setTabCompleter(announceCommand);

        SpeedCommand speedCommand = new SpeedCommand();
        KeeleCore.getInstance().getCommand("speed").setExecutor(speedCommand);
        KeeleCore.getInstance().getCommand("speed").setTabCompleter(speedCommand);

        SetSpawnCommand setSpawnCommand = new SetSpawnCommand();
        KeeleCore.getInstance().getCommand("setspawn").setExecutor(setSpawnCommand);
        KeeleCore.getInstance().getCommand("setspawn").setTabCompleter(setSpawnCommand);

        SpawnCommand spawnCommand = new SpawnCommand();
        KeeleCore.getInstance().getCommand("spawn").setExecutor(spawnCommand);
        KeeleCore.getInstance().getCommand("spawn").setTabCompleter(spawnCommand);

        HubCommand hubCommand = new HubCommand();
        KeeleCore.getInstance().getCommand("hub").setExecutor(hubCommand);

        DiscordCommand discordCommand = new DiscordCommand();
        KeeleCore.getInstance().getCommand("discord").setExecutor(discordCommand);
        KeeleCore.getInstance().getCommand("discord").setTabCompleter(discordCommand);

        RulesCommand rulesCommand = new RulesCommand();
        KeeleCore.getInstance().getCommand("rules").setExecutor(rulesCommand);

        RankCommand rankCommand = new RankCommand();
        KeeleCore.getInstance().getCommand("rank").setExecutor(rankCommand);
        KeeleCore.getInstance().getCommand("rank").setTabCompleter(rankCommand);

        GalaxyCommand galaxyCommand = new GalaxyCommand();
        KeeleCore.getInstance().getCommand("galaxy").setExecutor(galaxyCommand);
        KeeleCore.getInstance().getCommand("galaxy").setTabCompleter(galaxyCommand);

        WhatAmICommand whatAmICommand = new WhatAmICommand();
        KeeleCore.getInstance().getCommand("whatami").setExecutor(whatAmICommand);
        KeeleCore.getInstance().getCommand("whatami").setTabCompleter(whatAmICommand);

        BanCommand banCommand = new BanCommand();
        KeeleCore.getInstance().getCommand("ban").setExecutor(banCommand);
        KeeleCore.getInstance().getCommand("ban").setTabCompleter(banCommand);

        UnbanCommand unbanCommand = new UnbanCommand();
        KeeleCore.getInstance().getCommand("unban").setExecutor(unbanCommand);
        KeeleCore.getInstance().getCommand("unban").setTabCompleter(unbanCommand);

        BanlistCommand banlistCommand = new BanlistCommand();
        KeeleCore.getInstance().getCommand("banlist").setExecutor(banlistCommand);
        KeeleCore.getInstance().getCommand("banlist").setTabCompleter(banlistCommand);

        DebugCommand debugCommand = new DebugCommand();
        KeeleCore.getInstance().getCommand("debug").setExecutor(debugCommand);
        KeeleCore.getInstance().getCommand("debug").setTabCompleter(debugCommand);

        FindItemCommand findItemCommand = new FindItemCommand();
        KeeleCore.getInstance().getCommand("finditem").setExecutor(findItemCommand);
        KeeleCore.getInstance().getCommand("finditem").setTabCompleter(findItemCommand);
    }
}
