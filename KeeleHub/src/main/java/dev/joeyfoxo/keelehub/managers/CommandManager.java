package dev.joeyfoxo.keelehub.managers;

import dev.joeyfoxo.keelehub.KeeleHub;
import dev.joeyfoxo.keelehub.commands.ForceFieldCommand;

public class CommandManager {

    public CommandManager() {

        ForceFieldCommand forceFieldCommand = new ForceFieldCommand();
        KeeleHub.keeleHub.getCommand("forcefield").setExecutor(forceFieldCommand);
        KeeleHub.keeleHub.getCommand("forcefield").setTabCompleter(forceFieldCommand);

    }

}
