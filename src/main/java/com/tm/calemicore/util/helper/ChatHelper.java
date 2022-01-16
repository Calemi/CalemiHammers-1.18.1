package com.tm.calemicore.util.helper;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;

import java.util.Objects;

/**
 * Use this class to help with chat.
 */
public class ChatHelper {

    /**
     * Used to send a message to everyone in the Level.
     * @param level The level.
     * @param message The message.
     */
    public static void broadcastMessage (Level level, MutableComponent message) {
        Objects.requireNonNull(level.getServer()).getPlayerList().broadcastMessage(message, ChatType.SYSTEM, Util.NIL_UUID);
    }
}
