package com.tm.calemicore.util.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tm.calemicore.util.screen.ScreenRect;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreenHelper {

    private static final int TEXTURE_SIZE = 256;
    private static final Minecraft mc = Minecraft.getInstance();

    public static void bindGuiTexture(ResourceLocation textureLocation) {
        RenderSystem.setShaderTexture(0, textureLocation);
    }

    public static void drawHoveringTextBox (PoseStack poseStack, ResourceLocation textureLocation, int mouseX, int mouseY, int zLevel, ScreenRect rect, TranslatableComponent... text) {

        if (rect.contains(mouseX, mouseY)) {
            drawTextBox(poseStack, textureLocation, mouseX + 8, mouseY - 10, 50, false, text);
        }
    }

    public static void drawTextBox(PoseStack poseStack, ResourceLocation textureLocation, int x, int y, int zLevel, boolean centeredString, TranslatableComponent... text) {

        poseStack.pushPose();
        poseStack.translate(0, 0, zLevel);

        int maxLength = mc.font.width(text[0]);

        List<TranslatableComponent> textToRender = new ArrayList<>(Arrays.asList(text));

        for (TranslatableComponent msg : textToRender) {

            if (mc.font.width(msg) > maxLength) {
                maxLength = mc.font.width(msg);
            }
        }

        bindGuiTexture(textureLocation);
        drawCappedRect(poseStack, x + (centeredString ? - maxLength / 2 : 0), y, 0, 0, zLevel, maxLength + 5, 13 + ((textToRender.size() - 1) * 9), 512, 512);

        poseStack.translate(0, 0, 100);
        for (int i = 0; i < textToRender.size(); i++) {

            TranslatableComponent msg = textToRender.get(i);
            mc.font.draw(poseStack, msg.withStyle(ChatFormatting.WHITE), x + 3 + (centeredString ? -(int)(mc.font.width(msg.getString()) / 2) : 0), y + 3 + (i * 9), 0xFFFFFF);
        }

        poseStack.translate(0, 0, 0);
        poseStack.popPose();
    }

    public static void drawCappedRect (PoseStack poseStack, int x, int y, int u, int v, int zLevel, int width, int height, int maxWidth, int maxHeight) {

        //TOP LEFT
        drawRect(poseStack, x, y, u, v, zLevel, Math.min(width - 2, maxWidth), Math.min(height - 2, maxHeight));

        //RIGHT
        if (width <= maxWidth) drawRect(poseStack, x + width - 2, y, u + maxWidth - 2, v, zLevel, 2, Math.min(height - 2, maxHeight));

        //BOTTOM
        if (height <= maxHeight) drawRect(poseStack, x, y + height - 2, u, v + maxHeight - 2, zLevel, Math.min(width - 2, maxWidth), 2);

        //BOTTOM RIGHT
        if (width <= maxWidth && height <= maxHeight) drawRect(poseStack, x + width - 2, y + height - 2, u + maxWidth - 2, v + maxHeight - 2, zLevel, 2, 2);
    }

    public static void drawRect (PoseStack poseStack, int x, int y, int u, int v, int zLevel, int width, int height) {

        int maxX = x + width;
        int maxY = y + height;

        int maxU = u + width;
        int maxV = v + height;

        float pixel = 1F / TEXTURE_SIZE;

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex((float) x, (float) maxY, zLevel).uv(u * pixel, maxV * pixel).endVertex();
        buffer.vertex((float) maxX, (float) maxY, zLevel).uv(maxU * pixel, maxV * pixel).endVertex();
        buffer.vertex((float) maxX, (float) y, zLevel).uv(maxU * pixel, v * pixel).endVertex();
        buffer.vertex((float) x, (float) y, zLevel).uv(u * pixel, v * pixel).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
    }

    public static void drawCenteredString (PoseStack poseStack, TranslatableComponent text, int x, int y, int zLevel, int color) {

        poseStack.pushPose();
        poseStack.translate(0, 0, 50 + zLevel);
        mc.font.draw(poseStack, text, x - (float) (mc.font.width(text) / 2), y, color);
        poseStack.popPose();
    }

    public static void drawColoredRect (int x, int y, int zLevel, int width, int height, int hex, float alpha) {

        float r = (hex >> 16) & 0xFF;
        float g = (hex >> 8) & 0xFF;
        float b = (hex) & 0xFF;

        float red = ((((r * 100) / 255) / 100));
        float green = ((((g * 100) / 255) / 100));
        float blue = ((((b * 100) / 255) / 100));

        int maxX = x + width;
        int maxY = y + height;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(x, maxY, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(maxX, maxY, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(maxX, y, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(x, y, zLevel).color(red, green, blue, alpha).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void drawItemStack (ItemRenderer itemRender, ItemStack stack, int x, int y) {
        itemRender.blitOffset = -100;
        itemRender.renderGuiItem(stack, x, y);
        itemRender.blitOffset = 0F;
    }
}
