package com.github.syptitan.slimefly;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.command.brigadier.BasicCommand;

public class SlimeFly extends JavaPlugin implements Listener {
    
    private int checkdelay;
    private int slowfalling_time;
    private boolean slowfalling_manual;
    private FlyEnableManager manager;

    private String message_enable;
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

    private String message_disable;
    private String message_auto_disable;
    private String message_no_perm;
    private String message_already_off;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        FileConfiguration config = getConfig();

        checkdelay = config.getInt("check-delay", 5);
        slowfalling_time = config.getInt("slowfalling-time", 5);
        slowfalling_manual = config.getBoolean("manual-slowfalling", false);

        message_enable = config.getString("string-enabled", "Claimfly enabled!");
        message_disable = config.getString("string-disabled", "Claimfly disabled!");
        message_auto_disable = config.getString("string-auto-disabled", "Claimfly disabled!");
        message_no_perm = config.getString("string-no-permission", "You can only claimfly in claims you own or are trusted in!");
        message_already_off = config.getString("string-already-off", "Claimfly already disabled");

        manager = new FlyEnableManager(this, slowfalling_time, slowfalling_manual);

        BasicCommand flycmd = new ClaimFlyCommand(manager, this);
        registerCommand("claimfly", flycmd);
        registerCommand("cfly", flycmd);
    }
    
    public int getCheckDelay() {
        return checkdelay;
    }

    

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (manager.getFlyEnabled(player)) {
            manager.setFly(player, false);
        }

    }
}
