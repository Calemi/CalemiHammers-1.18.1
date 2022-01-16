package com.tm.calemicore.util.helper;

import com.tm.calemicore.util.Location;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;

/**
 * Use this class to help with Level Effects.
 */
public class LevelEffectHelper {


    /**
     * Summons a bolt of lightning at a Location
     * @param location The Location to summon at.
     * @param visualOnly Does the lightning bolt cause no harm?
     */
    public static void spawnLightning(Location location, boolean visualOnly) {

        if (!location.level.isClientSide()){
            ServerLevel serverLevel = (ServerLevel) location.level;

            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, location.level);
            bolt.setPos(location.x + 0.5D, location.y, location.z + 0.5D);
            bolt.setVisualOnly(visualOnly);
            serverLevel.addFreshEntity(bolt);
        }
    }

    /**
     * Starts rain in the current Level.
     * @param level The Level to start rain in.
     * @param durationTicks The duration of the rain in ticks.
     * @param isStorm Is the rain also a storm?
     */
    public static void startRain(Level level, int durationTicks, boolean isStorm) {

        if (!level.isClientSide()) {

            ServerLevel serverWorld = (ServerLevel) level;
            serverWorld.setWeatherParameters(0, durationTicks, true, isStorm);
        }
    }
}
