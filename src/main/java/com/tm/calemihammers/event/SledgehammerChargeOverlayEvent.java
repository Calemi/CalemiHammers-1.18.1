package com.tm.calemihammers.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tm.calemicore.util.helper.MathHelper;
import com.tm.calemicore.util.helper.ScreenHelper;
import com.tm.calemicore.util.screen.ScreenRect;
import com.tm.calemihammers.item.ItemSledgehammer;
import com.tm.calemihammers.main.CHReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SledgehammerChargeOverlayEvent {

    /**
     * Handles the Sledgehammer's charge overlay.
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void render (RenderGameOverlayEvent.Post event) {

        //Checks if the current render is on the "TEXT" layer.
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

            Minecraft mc = Minecraft.getInstance();
            Level level = mc.level;
            LocalPlayer player = mc.player;

            //Checks if the Player exists.
            if (player != null) {

                ItemStack activeItemStack = player.getUseItem();

                int scaledWidth = mc.getWindow().getGuiScaledWidth();
                int scaledHeight = mc.getWindow().getGuiScaledHeight();
                int midX = scaledWidth / 2;
                int midY = scaledHeight / 2;

                //Checks if the active Item is a Sledgehammer. Active Items are the ones being used; like charging a bow.
                if (activeItemStack.getItem() instanceof ItemSledgehammer sledgehammer) {

                    int chargeTime = sledgehammer.chargeTime;
                    int hammerIconWidth = 13;
                    int scaledChargeTime = MathHelper.scaleInt(player.getTicksUsingItem(), chargeTime, hammerIconWidth);

                    RenderSystem.setShaderTexture(0, new ResourceLocation(CHReference.MOD_ID + ":textures/gui/hammer_overlay.png"));

                    //If the Sledgehammer is charging, render the loading charge overlay.
                    if (player.getTicksUsingItem() < chargeTime) {
                        ScreenHelper.drawRect(event.getMatrixStack(), 0, 0, new ScreenRect(midX - 7, midY - 11, hammerIconWidth, 4), 0);
                        ScreenHelper.drawRect(event.getMatrixStack(), hammerIconWidth, 0, new ScreenRect(midX - 7, midY - 11, scaledChargeTime, 4), 0);
                    }

                    //If the Sledgehammer is ready, render the flashing ready overlay.
                    else {

                        if (level.getGameTime() % 5 > 1) {
                            ScreenHelper.drawRect(event.getMatrixStack(), hammerIconWidth * 2, 0, new ScreenRect(midX - 7, midY - 11, hammerIconWidth, 4), 0);
                        }
                    }
                }
            }
        }
    }
}
