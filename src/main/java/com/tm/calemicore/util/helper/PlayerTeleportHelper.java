package com.tm.calemicore.util.helper;

import com.tm.calemicore.util.Location;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerPlayer;
import java.util.EnumSet;
import java.util.Set;

/**
 * Used to help with teleporting Players.
 */
public class PlayerTeleportHelper {

    /**
     * Teleports a Player to a Location with yaw and pitch.
     * @param player The player to teleport.
     * @param location The Location to teleport to.
     * @param yaw The yaw to set.
     * @param pitch The pitch to set.
     */
    public static void teleportPlayer(ServerPlayer player, Location location, float yaw, float pitch) {
        teleport(player, location, yaw, pitch);
    }

    /**
     * Teleports a Player to a Location with yaw. Keeps pitch.
     * @param player The player to teleport.
     * @param location The Location to teleport to.
     * @param yaw The yaw to set.
     */
    public static void teleportPlayer(ServerPlayer player, Location location, float yaw) {
        teleport(player, location, yaw, 0);
    }

    /**
     * Teleports a Player to a Location. Keeps yaw and pitch.
     * @param player The player to teleport.
     * @param location The Location to teleport to.
     */
    public static void teleportPlayer(ServerPlayer player, Location location) {
        teleport(player, location, 0, 0);
    }

    /**
     * Teleports a Player to a Location with yaw and pitch.
     * @param player The player to teleport.
     * @param location The Location to teleport to.
     * @param yaw The yaw to set.
     * @param pitch The pitch to set.
     */
    private static void teleport(ServerPlayer player, Location location, float yaw, float pitch) {

        Set<ClientboundPlayerPositionPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);

        player.stopRiding();
        player.connection.teleport(location.x + 0.5F, location.y, location.z + 0.5F, yaw, pitch, set);

        if (!player.isFallFlying()) {
            player.setDeltaMovement(0, 0, 0);
            player.setOnGround(true);
        }
    }

    /**
     * @param legLocation The Location of the Player's legs.
     * @return true, if the given Location is clear.
     */
    public static boolean canTeleportAt(Location legLocation) {

        Location groundLocation = new Location(legLocation, Direction.DOWN);
        Location headLocation = new Location(legLocation, Direction.UP);

        return groundLocation.doesBlockHaveCollision() && !legLocation.doesBlockHaveCollision() && !headLocation.doesBlockHaveCollision();
    }
}
