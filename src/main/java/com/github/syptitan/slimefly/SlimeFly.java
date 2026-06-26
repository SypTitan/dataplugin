package com.github.syptitan.slimefly;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.command.brigadier.BasicCommand;

public class SlimeFly extends JavaPlugin implements Listener {
    
    private FlyEnableManager manager;

    private int checkdelay;
    private int slowfalling_time;

    private boolean slowfalling_manual;
    private boolean griefprevention_enabled;
    private boolean plotsquared_enabled;

    private String message_enable;
    private String message_disable;
    private String message_auto_disable;
    private String message_no_perm;
    private String message_already_off;
    private String message_already_flying;


    
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        
        FileConfiguration config = getConfig();
        
        checkdelay = config.getInt("check-delay");
        slowfalling_time = config.getInt("slowfalling-time");
        slowfalling_manual = config.getBoolean("manual-slowfalling");

        griefprevention_enabled = config.getBoolean("enable-griefprevention");
        plotsquared_enabled = config.getBoolean("enable-plotsquared");

        if (!Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) { 
            griefprevention_enabled = false; 
            Bukkit.getLogger().warning("[SlimeFly] Griefprevention is enabled in config but not present!");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            plotsquared_enabled = false;
            Bukkit.getLogger().warning("[SlimeFly] PlotSquared is enabled in config but not present!");
        }

        if (!griefprevention_enabled && !plotsquared_enabled) {
            Bukkit.getLogger().warning("[SlimeFly] No claim providers have been enabled, claimfly will be useless");
        }
        
        message_enable = config.getString("string-enabled");
        message_disable = config.getString("string-disabled");
        message_auto_disable = config.getString("string-auto-disabled");
        message_no_perm = config.getString("string-no-permission");
        message_already_off = config.getString("string-already-off");
        message_already_flying = config.getString("string-already-flying");
        

        
        manager = new FlyEnableManager(this);
        
        BasicCommand flycmd = new ClaimFlyCommand(manager, this);
        registerCommand("claimfly", flycmd);
        registerCommand("cfly", flycmd);

        registerCommand("claimflyreload", new ReloadCommand(this));
    }

    public void reloadConfiguration() {
        reloadConfig();
        FileConfiguration config = getConfig();

        checkdelay = config.getInt("check-delay");
        slowfalling_time = config.getInt("slowfalling-time");
        slowfalling_manual = config.getBoolean("manual-slowfalling");

        griefprevention_enabled = config.getBoolean("enable-griefprevention");
        plotsquared_enabled = config.getBoolean("enable-plotsquared");

        if (!Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) { 
            griefprevention_enabled = false; 
            Bukkit.getLogger().warning("[SlimeFly] Griefprevention is enabled in config but not present!");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            plotsquared_enabled = false;
            Bukkit.getLogger().warning("[SlimeFly] PlotSquared is enabled in config but not present!");
        }

        if (!griefprevention_enabled && !plotsquared_enabled) {
            Bukkit.getLogger().warning("[SlimeFly] No claim providers have been enabled, claimfly will be useless");
        }
        
        message_enable = config.getString("string-enabled");
        message_disable = config.getString("string-disabled");
        message_auto_disable = config.getString("string-auto-disabled");
        message_no_perm = config.getString("string-no-permission");
        message_already_off = config.getString("string-already-off");
        message_already_flying = config.getString("string-already-flying");

        manager.reloadConfig();
    }
    
    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (manager.getFlyEnabled(player)) {
            manager.setFly(player, false);
        }
        
    }

    public boolean getGPEnabled() {
        return griefprevention_enabled;
    }

    public boolean getP2Enabled() {
        return plotsquared_enabled;
    }
    
    public int getCheckDelay() {
        return checkdelay;
    }

    public int getSlowfallingTime() {
        return slowfalling_time;
    }

    public boolean getSlowfallingManual() {
        return slowfalling_manual;
    }

    
    public String getMessage_enable() {
        return message_enable;
    }

    public String getMessage_disable() {
        return message_disable;
    }

    public String getMessage_auto_disable() {
        return message_auto_disable;
    }

    public String getMessage_no_perm() {
        return message_no_perm;
    }

    public String getMessage_already_off() {
        return message_already_off;
    }

    public String getMessage_already_flying() {
        return message_already_flying;
    }

}
