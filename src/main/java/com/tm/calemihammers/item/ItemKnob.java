package com.tm.calemihammers.item;

import com.tm.calemihammers.init.InitItems;
import com.tm.calemihammers.main.CalemiHammers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

/**
 * The base class for Knobs.
 */
public class ItemKnob extends Item {

    /**
     * Creates a Knob Item.
     */
    public ItemKnob () {
        this(new Item.Properties().tab(CalemiHammers.TAB));
    }

    /**
     * Creates a Knob Item.
     * @param properties The properties of the Item.
     */
    public ItemKnob (Item.Properties properties) {
        super(properties);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        if (stack.getItem() == InitItems.KNOB_STARLIGHT.get()) {
            return Rarity.RARE;
        }

        return super.getRarity(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this == InitItems.SLEDGEHAMMER_NETHERITE.get() || this == InitItems.SLEDGEHAMMER_STARLIGHT.get();
    }
}
