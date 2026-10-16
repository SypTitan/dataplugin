package com.github.syptitan.fogswamp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class FogSwampBootStrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY.newHandler(
            event -> {
                try {
                    URI uri = this.getClass().getResource("/foggy_pack").toURI();
                    event.registrar().discoverPack(uri, "provided");
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        ));
    }
}
