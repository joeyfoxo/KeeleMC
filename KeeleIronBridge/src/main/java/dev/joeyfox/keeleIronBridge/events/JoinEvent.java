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

            plugin.getLogger().info("Sending rank query for player: " + player.getUsername());

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("get_rank");
            out.writeUTF(player.getUniqueId().toString());

            server.sendPluginMessage(CHANNEL, out.toByteArray());
        } else {
            plugin.getLogger().warn("Player " + player.getUsername() + " is not connected to any server.");
        }
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(CHANNEL)) return;
        if (!(event.getSource() instanceof ServerConnection)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subchannel = in.readUTF();

        if (subchannel.equals("rank_response")) {
            UUID uuid = UUID.fromString(in.readUTF());
            String rank = in.readUTF();

            plugin.getLogger().info("Received rank for " + uuid + ": " + rank);
            plugin.getLogger().info("Applying permissions for rank: " + rank);

            // TODO: Implement permission assignment based on the received rank
        }

        event.setResult(PluginMessageEvent.ForwardResult.handled());
    }
}