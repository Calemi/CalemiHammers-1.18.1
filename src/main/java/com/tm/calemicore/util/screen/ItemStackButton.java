package com.tm.calemicore.util.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ItemStackButton extends Button {

    protected ScreenRect rect;
    protected ResourceLocation textureLocation;
    protected final ItemRenderer itemRenderer;

    /**
     * A base button used to render a given ItemStack.
     * @param pressable Called when the button is pressed.
     */
    public ItemStackButton(int x, int y, ResourceLocation textureLocation, ItemRenderer itemRender, OnPress pressable) {
        super(x, y, 16, 16, new TextComponent(""), pressable);
        rect = new ScreenRect(this.x, this.y, width, height);
        this.textureLocation = textureLocation;
        this.itemRenderer = itemRender;
    }

    public abstract ItemStack getRenderedStack();
    public abstract TranslatableComponent[] getTooltip();

    public void setRect(ScreenRect rect) {
        this.rect = rect;
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    @Override
    public void renderButton (PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

        if (this.visible && this.active) {

            isHovered = rect.contains(mouseX, mouseY);

            ScreenHelper.drawItemStack(itemRenderer, getRenderedStack(), rect.x, rect.y);
            ScreenHelper.drawHoveringTextBox(poseStack, textureLocation, mouseX, mouseY, 150, rect, getTooltip());
        }
    }
}
