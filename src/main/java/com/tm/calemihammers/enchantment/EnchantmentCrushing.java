package com.tm.calemihammers.enchantment;

import com.tm.calemihammers.item.ItemSledgehammer;
import com.tm.calemihammers.main.CHReference;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CHReference.MOD_ID)
public class EnchantmentCrushing extends Enchantment {

    private static final EnchantmentCategory HAMMER = EnchantmentCategory.create("weapons", (item) -> (item instanceof ItemSledgehammer));

    public EnchantmentCrushing() {
        super(Rarity.UNCOMMON, HAMMER, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel () {
        return 2;
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return super.getMaxCost(enchantmentLevel) + 50;
    }
}
