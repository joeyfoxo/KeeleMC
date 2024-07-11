package dev.joey.keelesurvival.managers.hook;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GriefPreventionHook {

    public static Claim getClaimAtLocation(Player p, Location loc) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(p.getUniqueId());
        return GriefPrevention.instance.dataStore.getClaimAt(loc, false, playerData.lastClaim);
    }

    public static boolean hasClaimInChunk(Chunk chunk) {

        for (Claim claim : GriefPrevention.instance.dataStore.getClaims()) {
            if (claim.getChunks().contains(chunk)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasClaimInLocation(Player p, Location loc) {
        return (getClaimAtLocation(p, loc) != null);
    }

    public static boolean isOwnerAtLocation(Player p, Location loc) {
        if (!hasClaimInLocation(p, loc))
            return false;
        return getClaimAtLocation(p, loc).getOwnerName().equalsIgnoreCase(p.getName());
    }

    public static boolean hasTrustAccess(Player player) {

        return getClaimAtLocation(player, player.getLocation()).hasExplicitPermission(player, ClaimPermission.Inventory)
                || getClaimAtLocation(player, player.getLocation()).hasExplicitPermission(player, ClaimPermission.Build);

    }
}
