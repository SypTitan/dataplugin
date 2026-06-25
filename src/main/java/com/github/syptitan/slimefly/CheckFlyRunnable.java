package com.github.syptitan.slimefly;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckFlyRunnable extends BukkitRunnable {
    /*
        Checks whether the player is online, flying, and in a claim
    */
    
    private final UUID uuid;
    private final FlyEnableManager manager;
    private final String disable_message;

    public CheckFlyRunnable(UUID uuid, FlyEnableManager manager, String disable_message) {
        this.uuid = uuid;
        this.manager = manager;
        this.disable_message = disable_message;
    }

    @Override
    public void run() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            this.cancel();
        }

        if (!manager.getFlyEnabled(player)) {
            this.cancel();
        }

        if (!manager.inValidClaim(player)) {
            manager.setFly(player, false);
            manager.applySlowfalling(player, false);
            player.sendMessage(disable_message);
            this.cancel();
        }
    }
}
