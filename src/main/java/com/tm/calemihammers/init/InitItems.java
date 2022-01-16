package com.tm.calemihammers.init;

import com.tm.calemihammers.item.ItemKnob;
import com.tm.calemihammers.item.ItemSledgehammer;
import com.tm.calemihammers.item.SledgehammerTiers;
import com.tm.calemihammers.main.CHReference;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Handles setting up the Items for the mod.
 */
public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CHReference.MOD_ID);

    /**
     * Called to initialize the Items.
     */
    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> KNOB_WOOD = ITEMS.register("knob_wood", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_STONE = ITEMS.register("knob_stone", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_IRON = ITEMS.register("knob_iron", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_GOLD = ITEMS.register("knob_gold", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_DIAMOND = ITEMS.register("knob_diamond", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_NETHERITE = ITEMS.register("knob_netherite", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_STARLIGHT = ITEMS.register("knob_starlight", ItemKnob::new);

    public static final RegistryObject<Item> SLEDGEHAMMER_WOOD = ITEMS.register("sledgehammer_wood", () -> new ItemSledgehammer(SledgehammerTiers.WOOD));
    public static final RegistryObject<Item> SLEDGEHAMMER_STONE = ITEMS.register("sledgehammer_stone", () -> new ItemSledgehammer(SledgehammerTiers.STONE));
    public static final RegistryObject<Item> SLEDGEHAMMER_IRON = ITEMS.register("sledgehammer_iron", () -> new ItemSledgehammer(SledgehammerTiers.IRON));
    public static final RegistryObject<Item> SLEDGEHAMMER_GOLD = ITEMS.register("sledgehammer_gold", () -> new ItemSledgehammer(SledgehammerTiers.GOLD));
    public static final RegistryObject<Item> SLEDGEHAMMER_DIAMOND = ITEMS.register("sledgehammer_diamond", () -> new ItemSledgehammer(SledgehammerTiers.DIAMOND));
    public static final RegistryObject<Item> SLEDGEHAMMER_NETHERITE = ITEMS.register("sledgehammer_netherite", () -> new ItemSledgehammer(SledgehammerTiers.NETHERITE));
    public static final RegistryObject<Item> SLEDGEHAMMER_STARLIGHT = ITEMS.register("sledgehammer_starlight", () -> new ItemSledgehammer(SledgehammerTiers.STARLIGHT));

    /**
     * Used to register an Item.
     * @param name The name of the Item.
     * @param sup The Item class.
     */
    public static RegistryObject<Item> regItem(String name, final Supplier<? extends Item> sup) {
        return ITEMS.register(name, sup);
    }
}
