package dev.joey.keelecore.admin.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.KeeleCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HubCommand implements CommandExecutor {

    public HubCommand() {
        KeeleCore.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(KeeleCore.getInstance(), "BungeeCord");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF("hub");
        player.sendPluginMessage(KeeleCore.getInstance(), "BungeeCord", output.toByteArray());

        return false;
    }
}
