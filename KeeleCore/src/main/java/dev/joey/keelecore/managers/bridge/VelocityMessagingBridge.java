package dev.joey.keelecore.managers.bridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.List;
import java.util.UUID;

public class VelocityMessagingBridge implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("keele:rank_query")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("get_rank")) {
            UUID targetUUID = UUID.fromString(in.readUTF());
            PermissionManager.getPlayer(targetUUID).thenAccept(keelePlayer -> {
                String rank = keelePlayer.getRank().name();
                List<String> permissions = keelePlayer.getRank().getPermissions();

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("rank_response");
                out.writeUTF(targetUUID.toString());
                out.writeUTF(rank);
                out.writeInt(permissions.size());
                permissions.forEach(out::writeUTF);

                player.sendPluginMessage(KeeleCore.getInstance(), "keele:rank_query", out.toByteArray());
            });
        }
    }
}