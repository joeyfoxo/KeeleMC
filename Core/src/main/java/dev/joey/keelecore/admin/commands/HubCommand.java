package dev.joey.keelecore.admin.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class HubCommand implements CommandExecutor {

    public HubCommand() {
        keeleCore.getServer().getMessenger().registerOutgoingPluginChannel(keeleCore, "BungeeCord");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF("hub");
        player.sendPluginMessage(keeleCore, "BungeeCord", output.toByteArray());

        return false;
    }
}
