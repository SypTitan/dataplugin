package com.github.syptitan.slimefly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.syptitan.slimefly.Checkers.Checker;

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
    
    private List<Checker> checkers;

    public FlyEnableManager(SlimeFly plugin) {
        this.plugin = plugin;
        this.slowfalling_time = plugin.getSlowfallingTime();
        this.slowfalling_manual = plugin.getSlowfallingManual();
        
        checkers = new ArrayList<>();
        if (plugin.getGPEnabled()) {
            checkers.add(new com.github.syptitan.slimefly.Checkers.CheckerGP());
        }
        if (plugin.getP2Enabled()) {
            checkers.add(new com.github.syptitan.slimefly.Checkers.CheckerP2());
        }
    }

    public void reloadConfig() {
        this.slowfalling_time = plugin.getSlowfallingTime();
        this.slowfalling_manual = plugin.getSlowfallingManual();
        
        if (checkers == null) {
            checkers = new ArrayList<>();
        }
        else {
            checkers.clear();
        }

        if (plugin.getGPEnabled()) {
            checkers.add(new com.github.syptitan.slimefly.Checkers.CheckerGP());
        }
        if (plugin.getP2Enabled()) {
            checkers.add(new com.github.syptitan.slimefly.Checkers.CheckerP2());
        }
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
        for (Checker checker : checkers) {
            if (checker.validFlight(player)) {
                return true;
            }
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
