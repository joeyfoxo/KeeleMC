package dev.joeyfox.keeleIronBridge;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.events.PlayerPermissionListener;
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

    private final Logger logger;

    @Inject
    public KeeleIronBridge(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        MinecraftChannelIdentifier channel = MinecraftChannelIdentifier.from("keele:playerinfo");
        proxy.getChannelRegistrar().register(channel);

        proxy.getEventManager().register(this, new PlayerPermissionListener());

        getLogger().info("Registered playerinfo channel and permission listener.");

    }

    public Logger getLogger() {
        return logger;
    }
}