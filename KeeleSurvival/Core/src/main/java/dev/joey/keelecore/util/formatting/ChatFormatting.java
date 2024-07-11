package dev.joey.keelecore.util.formatting;

import dev.joey.keelecore.util.UtilClass;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;

public class ChatFormatting implements Listener {
    private Component format;

    private Chat vaultChat = null;

    public ChatFormatting() {
        UtilClass.keeleCore.saveDefaultConfig();
        reloadConfigValues();
        refreshVault();
        UtilClass.keeleCore.getServer().getPluginManager().registerEvents(this, UtilClass.keeleCore);
    }

    private void reloadConfigValues() {
        this.format = Component.text(UtilClass.keeleCore.getConfig().getString("format", "<{prefix}{name}{suffix}>: {message}"));
    }

    private void refreshVault() {
        Chat vaultChat = UtilClass.keeleCore.getServer().getServicesManager().load(Chat.class);
        if (vaultChat != this.vaultChat)
            UtilClass.keeleCore.getLogger().info("New Vault Chat implementation registered: " + ((vaultChat == null) ? "null" : vaultChat.getName()));
        this.vaultChat = vaultChat;
    }

    @EventHandler
    public void onServiceChange(ServiceRegisterEvent e) {
        if (e.getProvider().getService() == Chat.class)
            refreshVault();
    }

    @EventHandler
    public void onServiceChange(ServiceUnregisterEvent e) {
        if (e.getProvider().getService() == Chat.class)
            refreshVault();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatLow(AsyncChatEvent e) {
        e.message(this.format);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatHigh(AsyncChatEvent e) {
        Component format = this.format;
        if (this.vaultChat != null) {
            String prefix = this.vaultChat.getPlayerPrefix(e.getPlayer());
            String suffix = this.vaultChat.getPlayerSuffix(e.getPlayer());

            Component prefixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
            Component suffixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(suffix);

            // Call the new method to handle the hover event for each group
            prefixComponent = handleGroupHoverEvent(prefixComponent);

            Component finalPrefixComponent = prefixComponent;
            format = format.replaceText(builder -> builder.match("\\{prefix\\}").replacement(finalPrefixComponent));
            format = format.replaceText(builder -> builder.match("\\{suffix\\}").replacement(suffixComponent));
        }
        format = format.replaceText(builder -> builder.match("\\{name\\}").replacement(e.getPlayer().getName()));
        format = format.replaceText(builder -> builder.match("\\{message\\}").replacement(e.originalMessage()));
        e.setCancelled(true);
        Audience audience = Bukkit.getServer();
        audience.sendMessage(format);
    }

    private Component handleGroupHoverEvent(Component prefixComponent) {

        String[] parts = LegacyComponentSerializer.legacyAmpersand().serialize(prefixComponent).split(" ");

        TextComponent.Builder builder = Component.text();

        for (String part : parts) {
            // Remove color codes
            String cleanPart = ChatColor.stripColor(part.replace('&', '§'));

            // Use the chat prefix as the string to compare
            if (cleanPart.equalsIgnoreCase("owner")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&c&lOwner&r\n\nOwners own the server and handle all the \nadministrative tasks"))));
            } else if (cleanPart.equalsIgnoreCase("dev")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&4&lDev&r\n\nDevelopers work behind the scenes to maintain the\nserver and give the best experience"))));
            } else if (cleanPart.equalsIgnoreCase("admin")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&4&lAdmin&r\n\nAdmins are in charge of keeping the \nserver running smoothly"))));
            } else if (cleanPart.equalsIgnoreCase("mod")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&b&lMod&r\n\nModerators enforce rules and provide \nhelp to players"))));
            } else if (cleanPart.equalsIgnoreCase("student")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&a&lStudent&r\n\nStudents are members of Keele University"))));
            } else if (cleanPart.equalsIgnoreCase("guest")) {
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()).hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacyAmpersand().deserialize("&9&lGuest&r\n\nGuests are either alumni of Keele \nor external invitees"))));
            } else {
                // If the part doesn't match any group, just append it without a hover event
                builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(part.trim()));
            }

            // Add a space after each part
            builder.append(Component.text(" "));
        }

        // Build the final component
        return builder.build();
    }
}