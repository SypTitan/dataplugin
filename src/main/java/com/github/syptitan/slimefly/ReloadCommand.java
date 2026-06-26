package com.github.syptitan.slimefly;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class ReloadCommand implements BasicCommand {
    
    SlimeFly plugin;

    public ReloadCommand(SlimeFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        plugin.reloadConfiguration();

        source.getSender().sendMessage("Claimfly configuration reloaded!");
    }

    @Override
    public String permission() {
        return "slimefly.admin";
    }

}
