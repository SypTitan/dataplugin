package com.github.syptitan.slimefly.Checkers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;

public class CheckerP2 implements Checker {

    PlotAPI p2api;

    public CheckerP2() {
        p2api = new PlotAPI();
    }

    public boolean validFlight(Player player) {
        UUID player_uuid = player.getUniqueId();
        PlotPlayer<?> wrapped_player = p2api.wrapPlayer(player_uuid);
        Plot plot = wrapped_player.getCurrentPlot();

        if (plot == null) {
            return false;
        }

        Set<UUID> valid_players = new HashSet<>();

        valid_players.addAll(plot.getOwners());
        valid_players.addAll(plot.getTrusted());
        valid_players.addAll(plot.getMembers());

        if (valid_players.contains(player_uuid)) {
            return true;
        }

        return false;
    }
}
