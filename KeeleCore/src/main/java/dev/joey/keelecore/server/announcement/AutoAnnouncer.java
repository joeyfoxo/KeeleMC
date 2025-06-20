package dev.joey.keelecore.server.announcement;

import dev.joey.keelecore.KeeleCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;


public class AutoAnnouncer {

    public AutoAnnouncer() {

        Bukkit.getScheduler().runTaskTimer(KeeleCore.getInstance(), () -> {
            List<String> announcerList = KeeleCore.getInstance().getConfig().getStringList("autoAnnouncer");
            Collections.shuffle(announcerList);
            Bukkit.broadcast(Component.text().content(ChatColor.translateAlternateColorCodes('&',"&a&lKEELE&7> " + announcerList.get(0)))
                    .clickEvent(ClickEvent.openUrl("https://forms.gle/YmKYYSRaUpRDWKaP7"))
                    .build());

        }, 0, KeeleCore.getInstance().getConfig().getInt("autoAnnounceTimer"));

    }

}
