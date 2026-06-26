package com.github.syptitan.slimefly.Checkers;

import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class CheckerGP implements Checker {
    
    public boolean validFlight(Player player) {
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

}
