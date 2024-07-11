package dev.joey.keelecore.server.announcement;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class AutoAnnouncer {

    public AutoAnnouncer() {

        Bukkit.getScheduler().runTaskTimer(keeleCore, () -> {
            List<String> announcerList = keeleCore.getConfig().getStringList("autoAnnouncer");
            Collections.shuffle(announcerList);
            Bukkit.broadcast(Component.text().content(ChatColor.translateAlternateColorCodes('&',"&a&lKEELE&7> " + announcerList.get(0)))
                    .clickEvent(ClickEvent.openUrl("https://forms.gle/YmKYYSRaUpRDWKaP7"))
                    .build());

        }, 0, keeleCore.getConfig().getInt("autoAnnounceTimer"));

    }

}
