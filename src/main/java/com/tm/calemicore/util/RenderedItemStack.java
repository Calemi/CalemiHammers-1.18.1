package com.tm.calemicore.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

/**
 * Used to render an Item Stack in the Level.
 */
public class RenderedItemStack {

    private ItemStack stack;

    public RenderedItemStack() {
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Renders the floating item in the world. Will not render an empty stack.
     */
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        if (!stack.isEmpty()) {
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, packedLight, packedOverlay, poseStack, buffer, 0);
        }
    }
}
