package com.github.syptitan.fogswamp;

import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.datapack.Datapack;

public final class FogSwamp extends JavaPlugin {

    @Override
    public void onLoad() {
        Datapack pack = this.getServer().getDatapackManager().getPack(getPluginMeta().getName() + "/provided");
        if (pack != null) {
            if (pack.isEnabled()) {
                this.getLogger().info("[FogSwamp] Datapack enabled!");
            }
            else {
                this.getLogger().info("[FogSwamp] Datapack not enabled!");
            }
        }
        else {
            this.getLogger().warning("[FogSwamp] Could not find datapack!");
        }
    }
}
