package com.tm.calemicore.util;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Used to send message from a "Unit".
 * Messages sent will have the Unit's name in brackets ex. [Furnace]
 */
public class UnitMessenger {

    /**
     * The Unit's name key.
     */
    private final String unitNameKey;

    /**
     * Creates a Unit Messenger.
     * @param unitNameKey The Unit's name key.
     */
    public UnitMessenger(String unitNameKey) {
        this.unitNameKey = unitNameKey;
    }

    /**
     * Sends a message to specified Players.
     * @param msgComponent The message to send.
     * @param players The Players to send to.
     */
    public void sendMessage(MutableComponent msgComponent, Player... players) {

        for (Entity player : players) {

            MutableComponent component = getUnitName().append(msgComponent.withStyle(ChatFormatting.GREEN));
            player.sendMessage(component, Util.NIL_UUID);
        }
    }

    /**
     * Sends an error message to specified Players.
     * @param msgComponent The message to send.
     * @param players The Players to send to.
     */
    public void sendErrorMessage(MutableComponent msgComponent, Player... players) {

        for (Entity player : players) {

            MutableComponent component = getUnitName().append(msgComponent.withStyle(ChatFormatting.RED));
            player.sendMessage(component, Util.NIL_UUID);
        }
    }

    /**
     * @param messageKey The message key.
     * @param args The arguments if needed.
     * @return The message component with the prefix already included.
     */
    public MutableComponent getMessage(String messageKey, Object... args) {
        return new TranslatableComponent("unit." + unitNameKey + ".msg." + messageKey, args);
    }

    /**
     * @return The Unit's name in brackets.
     */
    private MutableComponent getUnitName() {
        return new TextComponent("[").append(new TranslatableComponent("unit." + unitNameKey + ".name")).append("] ");
    }
}
