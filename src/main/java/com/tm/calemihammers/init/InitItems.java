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

    public static final RegistryObject<Item> KNOB_WOOD =              regItem("knob_wood", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_STONE =             regItem("knob_stone", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_IRON =              regItem("knob_iron", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_GOLD =              regItem("knob_gold", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_DIAMOND =           regItem("knob_diamond", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_NETHERITE =         regItem("knob_netherite", ItemKnob::new);
    public static final RegistryObject<Item> KNOB_STARLIGHT =         regItem("knob_starlight", ItemKnob::new);

    public static final RegistryObject<Item> SLEDGEHAMMER_WOOD =      regItem("sledgehammer_wood", () -> new ItemSledgehammer(SledgehammerTiers.WOOD));
    public static final RegistryObject<Item> SLEDGEHAMMER_STONE =     regItem("sledgehammer_stone", () -> new ItemSledgehammer(SledgehammerTiers.STONE));
    public static final RegistryObject<Item> SLEDGEHAMMER_IRON =      regItem("sledgehammer_iron", () -> new ItemSledgehammer(SledgehammerTiers.IRON));
    public static final RegistryObject<Item> SLEDGEHAMMER_GOLD =      regItem("sledgehammer_gold", () -> new ItemSledgehammer(SledgehammerTiers.GOLD));
    public static final RegistryObject<Item> SLEDGEHAMMER_DIAMOND =   regItem("sledgehammer_diamond", () -> new ItemSledgehammer(SledgehammerTiers.DIAMOND));
    public static final RegistryObject<Item> SLEDGEHAMMER_NETHERITE = regItem("sledgehammer_netherite", () -> new ItemSledgehammer(SledgehammerTiers.NETHERITE));
    public static final RegistryObject<Item> SLEDGEHAMMER_STARLIGHT = regItem("sledgehammer_starlight", () -> new ItemSledgehammer(SledgehammerTiers.STARLIGHT));

    /**
     * Used to register an Item.
     * @param name The name of the Item.
     * @param sup The Item class.
     */
    public static RegistryObject<Item> regItem(String name, final Supplier<? extends Item> sup) {
        return ITEMS.register(name, sup);
    }
}
