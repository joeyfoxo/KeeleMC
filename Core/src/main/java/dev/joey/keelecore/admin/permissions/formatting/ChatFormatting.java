package dev.joey.keelecore.admin.permissions.formatting;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PermissionManager;
import dev.joey.keelecore.util.UtilClass;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatFormatting implements Listener {
    private Component format;

    public ChatFormatting() {
        UtilClass.keeleCore.saveDefaultConfig();
        reloadConfigValues();
        UtilClass.keeleCore.getServer().getPluginManager().registerEvents(this, UtilClass.keeleCore);
    }

    private void reloadConfigValues() {
        this.format = Component.text(UtilClass.keeleCore.getConfig().getString("format", "<{prefix}{name}{suffix}>: {message}"));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatLow(AsyncChatEvent e) {
        e.setCancelled(true); // prevent default handling
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatHigh(AsyncChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        KeelePlayer kp = PermissionManager.getCached(uuid);

        if (kp == null) {
            e.getPlayer().sendMessage(Component.text("§cFailed to load your player rank."));
            return;
        }

        Component format = this.format;
        Component prefixComponent = handleGroupHoverEvent(kp.getRank());
        Component suffixComponent = kp.getRank().getSuffix();

        format = format.replaceText(builder -> builder.match("\\{prefix\\}").replacement(prefixComponent));
        format = format.replaceText(builder -> builder.match("\\{suffix\\}").replacement(suffixComponent));
        format = format.replaceText(builder -> builder.match("\\{name\\}").replacement(Component.text(e.getPlayer().getName())));
        format = format.replaceText(builder -> builder.match("\\{message\\}").replacement(e.originalMessage()));

        Component finalFormat = format;
        Bukkit.getScheduler().runTask(UtilClass.keeleCore, () -> {
            Audience audience = Bukkit.getServer();
            audience.sendMessage(finalFormat);
        });
    }

    private Component handleGroupHoverEvent(PlayerRank rank) {
        Component prefix = rank.getPrefix(); // still serialized, e.g., "&c[Owner]"
        TextComponent.Builder builder = Component.text();

        Component hoverText;

        switch (rank) {
            case OWNER -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&c&lOwner&r\n\nOwners own the server and handle all the \nadministrative tasks");
            case DEV -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&4&lDev&r\n\nDevelopers work behind the scenes to maintain the\nserver and give the best experience");
            case ADMIN -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&4&lAdmin&r\n\nAdmins are in charge of keeping the \nserver running smoothly");
            case MOD -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&b&lMod&r\n\nModerators enforce rules and provide \nhelp to players");
            case STUDENT -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&a&lStudent&r\n\nStudents are members of Keele University");
            case GUEST -> hoverText = LegacyComponentSerializer.legacyAmpersand().deserialize("&9&lGuest&r\n\nGuests are either alumni of Keele \nor external invitees");
            default -> hoverText = null;
        }

        if (hoverText != null) {
            builder.append(prefix.hoverEvent(HoverEvent.showText(hoverText)));
        } else {
            builder.append(prefix);
        }

        return builder.build();
    }
}