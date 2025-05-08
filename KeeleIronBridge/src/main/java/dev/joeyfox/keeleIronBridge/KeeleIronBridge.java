package dev.joeyfox.keeleIronBridge;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.joeyfox.keeleIronBridge.events.IronPermissionProvider;
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
        proxy.getChannelRegistrar().register(MinecraftChannelIdentifier.create("keele", "rank"));
        proxy.getEventManager().register(this, new PlayerPermissionListener(proxy));
        logger.info("[KeeleIronBridge] Registered plugin messaging channel: keele:rank");
    }

    @Subscribe
    public void on(PermissionsSetupEvent event) {
        event.setProvider(new IronPermissionProvider());
    }

    public Logger getLogger() {
        return logger;
    }

    public ProxyServer getProxy() {
        return proxy;
    }
}