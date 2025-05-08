package dev.joeyfox.keeleIronBridge;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.events.JoinEvent;
import org.slf4j.Logger;

@Plugin(
        id = "keeleironbridge",
        name = "KeeleIronBridge",
        version = "1.0",
        description = "Just Another Furry Proxy",
        url = "https://joeyfox.dev",
        authors = {"Joeyfoxo"}
)
public class KeeleIronBridge {

    private final ProxyServer proxy;

    @Inject
    public KeeleIronBridge(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Register the plugin message channel
        proxy.getChannelRegistrar().register(MinecraftChannelIdentifier.from("keele:rank_query"));
        // Register the event listener
        proxy.getEventManager().register(this, new JoinEvent(this));
    }
}