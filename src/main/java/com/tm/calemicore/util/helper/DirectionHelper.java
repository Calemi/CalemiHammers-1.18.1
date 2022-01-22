package com.tm.calemicore.util.helper;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class DirectionHelper {

    public static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};

    /**
     * Gets the horizontal Direction the Player is looking towards.
     */
    public static Direction getPlayerHorizontalDirection(Player player) {
        int face = Mth.floor((double) (player.getYRot() * 4.0F / 360.0F) + 0.5D) & 3;
        return HORIZONTAL_DIRECTIONS[face];
    }
}

