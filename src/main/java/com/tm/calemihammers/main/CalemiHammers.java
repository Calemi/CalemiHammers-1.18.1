package com.tm.calemihammers.main;

import com.tm.calemihammers.config.CHConfig;
import com.tm.calemihammers.event.SledgehammerChargeOverlayEvent;
import com.tm.calemihammers.init.InitEnchantments;
import com.tm.calemihammers.init.InitItems;
import com.tm.calemihammers.item.CHTagItemLists;
import com.tm.calemihammers.tab.CHTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The main class for Calemi's Sledgehammers
 */
@Mod(CHReference.MOD_ID)
public class CalemiHammers {

    /**
     * A reference to the instance of the mod.
     */
    public static CalemiHammers instance;

    /**
     * Used to register the client and common setup methods.
     */
    public static IEventBus MOD_EVENT_BUS;

    public static final CreativeModeTab TAB = new CHTab();

    /**
     * Everything starts here.
     */
    public CalemiHammers() {

        //Initializes the instance.
        instance = this;

        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MOD_EVENT_BUS.addListener(this::onClientSetup);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, CHTagItemLists::onWorldLoad);

        InitItems.init();
        InitEnchantments.init();
        CHConfig.init();    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SledgehammerChargeOverlayEvent());
    }
}
