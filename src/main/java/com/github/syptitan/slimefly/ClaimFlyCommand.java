package com.github.syptitan.slimefly;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

@NullMarked
public class ClaimFlyCommand implements BasicCommand {
    /*
        Sets your claimfly status

        Mainly does a lot of parsing, the real flight logic happens in FlyEnableManager
    */
    
    FlyEnableManager manager;
    SlimeFly plugin;

    ClaimFlyCommand(FlyEnableManager manager, SlimeFly plugin) {
        this.manager = manager;
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {


        Entity executor = source.getExecutor();
        if (!(source.getExecutor() instanceof Player)) return;
        Player player = (Player) executor;

        CommandSender sender = source.getSender();

        if (args.length > 1) {
            sender.sendMessage("/claimfly <on|off|toggle>");
            return;
        }

        if (args.length == 1 && !List.of("on", "off", "toggle").contains(args[0])) {
            sender.sendMessage("/claimfly <on|off|toggle>");
            return;
        }

        boolean newState = false;

        if (args.length == 0 || args[0].equals("toggle")) {
            boolean currentState = manager.getFlyEnabled(player);
            newState = !currentState;
        }
        else {
            if (args[0].equals("on")) {
                newState = true;
            }
    
            if (args[0].equals("off")) {
                newState = false;
            }
        }

        if (newState == true) {
            if (!manager.inValidClaim(player)) {
                player.sendMessage(plugin.getMessage_no_perm());
                return;
            }
            if (player.getAllowFlight()) {
                player.sendMessage(plugin.getMessage_already_flying());
                return;
            }
            manager.setFly(player, true);
            player.sendMessage(plugin.getMessage_enable());
        }
        else {
            if (!manager.getFlyEnabled(player)) {
                player.sendMessage(plugin.getMessage_already_off());
                return;
            }
            manager.setFly(player, false);
            manager.applySlowfalling(player, true);
            player.sendMessage(plugin.getMessage_disable());
        }
    }

    @Override
    public String permission() {
        return "slimefly.use";
    }
}
