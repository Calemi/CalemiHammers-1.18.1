package com.tm.calemicore.util.helper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

/**
 * Use this class for adding lore to Items.
 */
public class LoreHelper {

    /**
     * Adds information lore to an Item.
     * @param tooltip The tooltip list of the Item.
     * @param loreComponent The translatable lore component to add.
     */
    public static void addInformationLore (List<Component> tooltip, MutableComponent loreComponent) {
        addInformationLore(tooltip, loreComponent, false);
    }

    /**
     * Adds information lore to an Item.
     * @param tooltip The tooltip list of the Item.
     * @param loreComponent The translatable lore component to add.
     * @param isFirst Is this line of information lore the first one.
     */
    public static void addInformationLore (List<Component> tooltip, MutableComponent loreComponent, boolean isFirst) {

        if (Screen.hasShiftDown()) {
            tooltip.add(loreComponent.withStyle(ChatFormatting.GRAY));
        }

        else if (isFirst) tooltip.add(getPlateText("lore.key.shift", ChatFormatting.AQUA).append(" ").append(new TranslatableComponent("lore.info").withStyle(ChatFormatting.GRAY)));
    }

    /**
     * Adds control lore to an Item.
     * @param tooltip The tooltip list of the Item.
     * @param loreComponent The translatable lore component to add.
     * @param controlType The type of control.
     */
    public static void addControlsLore (List<Component> tooltip, MutableComponent loreComponent, ControlType controlType) {
        addControlsLore(tooltip, loreComponent, controlType, false);
    }

    /**
     * Adds control lore to an Item.
     * @param tooltip The tooltip list of the Item.
     * @param loreComponent The translatable lore component to add.
     * @param controlType The type of control.
     * @param isFirst Is this line of control lore the first one.
     */
    public static void addControlsLore (List<Component> tooltip, MutableComponent loreComponent, ControlType controlType, boolean isFirst) {

        if (Screen.hasControlDown()) {
            addActionLore(tooltip, loreComponent.withStyle(ChatFormatting.GRAY), controlType);
        }

        else if (isFirst) tooltip.add(getPlateText("lore.key.ctrl", ChatFormatting.AQUA).append(" ").append(new TranslatableComponent("lore.controls").withStyle(ChatFormatting.GRAY)));
    }

    /**
     * Helper method to add a control lore line to an Item.
     * @param tooltip The tooltip list of the Item.
     * @param loreComponent The translatable lore component to add.
     * @param controlType The type of control.
     */
    private static void addActionLore (List<Component> tooltip, MutableComponent loreComponent, ControlType controlType) {
        tooltip.add(getPlateText(controlType.getName(), ChatFormatting.YELLOW).append(" ").append(loreComponent));
    }

    /**
     * Adds a blank line of lore to an Item.
     * @param tooltip The tooltip list of the Item.
     */
    public static void addBlankLine (List<Component> tooltip) {
        tooltip.add(new TextComponent(""));
    }

    /**
     * @param valueKey The key used to translate the string.
     * @param format The color used in the middle.
     * @return A String with surrounding brackets and color in the middle.
     */
    public static MutableComponent getPlateText (String valueKey, ChatFormatting format) {
        return new TextComponent(ChatFormatting.GRAY + "[").append(new TranslatableComponent(valueKey).withStyle(format)).append(ChatFormatting.GRAY + "]");
    }

    public enum ControlType {

        USE("use"),
        USE_OPEN_HAND("use-open-hand"),
        SNEAK_USE("sneak-use"),
        SNEAK_USE_BOOK("sneak-use-link-book"),
        SNEAK_BREAK_BLOCK("sneak-break-block"),
        RELEASE_USE("release-use"),
        LEFT_CLICK_BLOCK("left-click-block"),
        SNEAK_LEFT_CLICK_BLOCK("sneak-left-click-block");

        private final String key;

        ControlType(String key) {
            this.key = key;
        }

        String getName() {
            return "lore.control_type." + key;
        }
    }
}

