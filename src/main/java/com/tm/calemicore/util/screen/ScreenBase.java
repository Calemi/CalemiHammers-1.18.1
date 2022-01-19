package com.tm.calemicore.util.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public abstract class ScreenBase extends Screen {

    protected final Player player;
    protected final InteractionHand hand;

    protected ScreenBase(Player player, InteractionHand hand) {

        super(new TextComponent("Help"));
        this.player = player;
        this.hand = hand;
    }

    /**
     * Used to obtain the GUI's texture, so it can render it.
     */
    protected abstract ResourceLocation getGuiTextureLocation ();

    /**
     * Used to render anything in the background layer.
     */
    protected abstract void drawGuiBackground (PoseStack poseStack, int mouseX, int mouseY);

    /**
     * Used to render anything in the foreground layer.
     */
    protected abstract void drawGuiForeground (PoseStack poseStack, int mouseX, int mouseY);

    /**
     * Used to determine the width of the GUI.
     */
    protected abstract int getGuiSizeX ();

    /**
     * Used to determine the height of the GUI.
     */
    protected abstract int getGuiSizeY ();

    /**
     * Used to determine the left of the GUI.
     */
    protected int getScreenX () {
        return (this.width - getGuiSizeX()) / 2;
    }

    /**
     * Used to determine the top of the GUI.
     */
    protected int getScreenY () {
        return (this.height - getGuiSizeY()) / 2;
    }

    protected abstract boolean canCloseWithInvKey ();

    /**
     * The base render method. Handles ALL rendering.
     */
    @Override
    public void render (PoseStack poseStack, int mouseX, int mouseY, float f1) {

        renderBackground(poseStack);

        if (getGuiTextureLocation() != null) {
            ScreenHelper.bindGuiTexture(getGuiTextureLocation());
            ScreenHelper.drawRect(poseStack, getScreenX(), getScreenY(), 0, 0, 0, getGuiSizeX(), getGuiSizeY());
        }

        drawGuiBackground(poseStack, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, f1);
        drawGuiForeground(poseStack, mouseX, mouseY);
    }

    /**
     * Handles closing the GUI when the inventory key is pressed.
     */
    @Override
    public boolean keyPressed (int i1, int i2, int i3) {
        super.keyPressed(i1, i2, i3);

        if (minecraft != null) {

            if (canCloseWithInvKey() && i1 == minecraft.options.keyInventory.getKey().getValue()) {
                player.closeContainer();
                return true;
            }
        }

        return false;
    }
}
