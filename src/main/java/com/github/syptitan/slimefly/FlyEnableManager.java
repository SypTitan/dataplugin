package com.github.syptitan.slimefly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class FlyEnableManager {
    /*
        This class handles storage of players, setting their actual flight, 
        running the checking Runnable and checking whether they are in the claim
        Also stores all the communication strings

        It's basically the main backend class everything else revolves around
    */
    
    private final Map<UUID, Boolean> claimFlyMap = new HashMap<>();
    private final SlimeFly plugin;
    private int slowfalling_time;
    private boolean slowfalling_manual;

    public FlyEnableManager(SlimeFly plugin, int slowfalling_time, boolean slowfalling_manual) {
        this.plugin = plugin;
        this.slowfalling_time = slowfalling_time;
        this.slowfalling_manual = slowfalling_manual;
    }

    public boolean getFlyEnabled(Player player) {
        Boolean isFlying = claimFlyMap.get(player.getUniqueId());
        if (isFlying == null) {
            setFly(player, false);
            return false;
        }
        return isFlying;
    }

    public void setFly(Player player, boolean willFly) {
        claimFlyMap.put(player.getUniqueId(), willFly);
        player.setAllowFlight(willFly);

        if (willFly) {
            new CheckFlyRunnable(player.getUniqueId(), this, plugin.getMessage_auto_disable()).runTaskTimer(plugin, 0, plugin.getCheckDelay());
        }
    }

    public boolean inValidClaim(Player player) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true, null);

        if (claim == null) {
            return false;
        }

        if (claim.getOwnerID() == player.getUniqueId()) {
            return true;
        }

        if (claim.checkPermission(player, ClaimPermission.Build, null) == null) {
            return true;
        }

        return false;
    }

    public void applySlowfalling(Player player, boolean manual) {
        if (slowfalling_time <= 0) return;

        if (!manual || (slowfalling_manual && manual)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, slowfalling_time*20, 0, false, true));
        }
    }
}
