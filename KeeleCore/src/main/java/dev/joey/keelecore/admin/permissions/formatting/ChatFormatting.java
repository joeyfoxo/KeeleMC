package dev.joey.keelecore.admin.permissions.formatting;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import dev.joey.keelecore.managers.PlayerPermManager;
import dev.joey.keelecore.util.UtilClass;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatFormatting implements Listener {

    private final String rawFormat;

    public ChatFormatting() {
        KeeleCore.getInstance().saveDefaultConfig();
        this.rawFormat = KeeleCore.getInstance().getConfig().getString("format", "{prefix}{name}{suffix}: {message}");
        KeeleCore.getInstance().getServer().getPluginManager().registerEvents(this, KeeleCore.getInstance());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onSpigotChatLow(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpigotChatHigh(AsyncPlayerChatEvent event) {

        if (UtilClass.isPaper) {
            event.setCancelled(true);
            return;
        }
        UUID uuid = event.getPlayer().getUniqueId();
        KeelePlayer kp = PlayerPermManager.getCached(uuid);

        if (kp == null) {
            event.getPlayer().sendMessage("§cFailed to load your player rank.");
            return;
        }

        Player player = event.getPlayer();

        String prefix = LegacyComponentSerializer.legacyAmpersand().serialize(handleGroupHoverEvent(kp.getRank()));
        String suffix = LegacyComponentSerializer.legacyAmpersand().serialize(kp.getRank().getSuffix());
        String name = player.getName();
        String message = event.getMessage();

        String formatted = rawFormat
                .replace("{prefix}", prefix)
                .replace("{name}", name)
                .replace("{suffix}", suffix)
                .replace("{message}", message);

        formatted = ChatColor.translateAlternateColorCodes('&', formatted);


        // Send formatted message as plain text to all players
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(formatted);
        }

        // Also send to console
        Bukkit.getConsoleSender().sendMessage(formatted);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatLow(AsyncChatEvent e) {
        e.setCancelled(true); // cancel default Adventure-based chat
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatHigh(AsyncChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        KeelePlayer kp = PlayerPermManager.getCached(uuid);

        if (kp == null) {
            e.getPlayer().sendMessage(Component.text("§cFailed to load your player rank."));
            return;
        }

        Player player = e.getPlayer();

        Component prefixComponent = handleGroupHoverEvent(kp.getRank());
        Component suffixComponent = kp.getRank().getSuffix();
        Component nameComponent = Component.text(player.getName());
        Component messageComponent = e.originalMessage();

        String formatted = rawFormat
                .replace("{prefix}", "<prefix>")
                .replace("{name}", "<name>")
                .replace("{suffix}", "<suffix>")
                .replace("{message}", "<message>");

        Component finalMessage = MiniMessage.miniMessage().deserialize(formatted)
                .replaceText(builder -> builder.match("<prefix>").replacement(prefixComponent))
                .replaceText(builder -> builder.match("<name>").replacement(nameComponent))
                .replaceText(builder -> builder.match("<suffix>").replacement(suffixComponent))
                .replaceText(builder -> builder.match("<message>").replacement(messageComponent));

        // Send to all players
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(finalMessage);
        }
    }

    private Component handleGroupHoverEvent(PlayerRank rank) {
        Component prefix = rank.getPrefix(); // May be a legacy-encoded Component
        TextComponent.Builder builder = Component.text();

        Component hoverText = switch (rank) {
            case OWNER -> deserialize("&c&lOWNER&r\n\nOwners own the server and oversee all \nadministrative tasks");
            case DEV -> deserialize("&4&lDDEV&r\n\nDevelopers work behind the scenes to maintain the\nserver and give the best experience");
            case ADMIN -> deserialize("&4&lADMIN&r\n\nAdmins are in charge of keeping the \nserver running smoothly");
            case MOD -> deserialize("&b&lMOD&r\n\nModerators enforce rules and provide \nhelp to players");
            case HELPER -> deserialize("&d&lHELPER&r\n\nHelpers are here to assist players \nwith any questions they may have");
            case STUDENT -> deserialize("&a&lSTUDENT&r\n\nStudents are members of Keele University");
            case GUEST-> deserialize("&9&lGUEST&r\n\nGuests are either alumni of Keele \nor external invitees");
            case PLAYER -> deserialize("&8&lPLAYER&r\n\nPlayers are the backbone of the server, \nengaging in activities and enjoying the game");
        };

        if (hoverText != null) {
            builder.append(prefix.hoverEvent(HoverEvent.showText(hoverText)));
        } else {
            builder.append(prefix);
        }

        return builder.build();
    }

    private Component deserialize(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }
}