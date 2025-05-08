package dev.joeyfox.keeleIronBridge.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.KeeleIronBridge;

import java.util.Optional;
import java.util.UUID;

public class JoinEvent {

    private final KeeleIronBridge plugin;
    private static final MinecraftChannelIdentifier CHANNEL = MinecraftChannelIdentifier.from("keele:rank_query");

    public JoinEvent(KeeleIronBridge plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        Optional<ServerConnection> serverConnection = player.getCurrentServer();

        if (serverConnection.isPresent()) {
            ServerConnection server = serverConnection.get();

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("get_rank");
            out.writeUTF(player.getUniqueId().toString());

            server.sendPluginMessage(CHANNEL, out.toByteArray());
        } else {
            System.out.println("Player {} is not connected to any server.");
        }
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) return;
        if (!(event.getSource() instanceof ServerConnection)) return;

        event.setResult(PluginMessageEvent.ForwardResult.handled());

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subchannel = in.readUTF();
        if (subchannel.equals("rank_response")) {
            UUID uuid = UUID.fromString(in.readUTF());
            String rank = in.readUTF();

            System.out.println(("Received rank for {}: {}" + uuid + rank));
            System.out.println("Applying permissions for rank: " + rank);
            System.out.println("Player UUID: " + uuid);

            // Implement logic to apply permissions based on rank here
        }
    }
}