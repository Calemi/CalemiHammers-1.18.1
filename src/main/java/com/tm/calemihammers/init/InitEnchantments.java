package com.tm.calemihammers.init;

import com.tm.calemihammers.enchantment.EnchantmentCrushing;
import com.tm.calemihammers.main.CHReference;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CHReference.MOD_ID);

    /**
     * Called to initialize the Items.
     */
    public static void init() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Enchantment> CRUSHING = ENCHANTMENTS.register("crushing", EnchantmentCrushing::new);
}