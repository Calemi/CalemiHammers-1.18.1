package com.tm.calemihammers.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public enum SledgehammerTiers implements Tier {

    WOOD      (20 * 10, 2F, 6F, 0, 15, 50, 1.2F, () -> {return Ingredient.of(ItemTags.PLANKS);}),
    STONE     (44 * 10, 4F, 7F, 1, 5, 35, 1.2F, () -> {return Ingredient.of(Items.STONE);}),
    IRON      (83 * 10, 6F, 8F, 2, 14, 25, 1.3F, () -> {return Ingredient.of(Items.IRON_INGOT);}),
    GOLD      (11 * 10, 12F, 6F, 0, 22, 15, 1.3F, () -> {return Ingredient.of(Items.GOLD_INGOT);}),
    DIAMOND   (520 * 10, 8F, 8F, 3, 10, 20, 1.3F, () -> {return Ingredient.of(Items.DIAMOND);}),
    NETHERITE (677 * 10, 9F, 9F, 4, 15, 15, 1.3F, () -> {return Ingredient.of(Items.NETHERITE_INGOT);}),
    STARLIGHT (10000000, 20F, 15F, 5, 25, 10, 1.3F, () -> {return Ingredient.of(Blocks.AIR);});

    public final int durability;
    public final float efficiency;
    public final float attackDamage;
    public final int harvestLevel;
    public final int enchantability;
    public final int baseChargeTime;
    public final float attackSpeed;
    public final LazyLoadedValue<Ingredient> repairMaterial;

    @SuppressWarnings("SameParameterValue")
    SledgehammerTiers(int durability, float efficiency, float attackDamage, int harvestLevel, int enchantability, int baseChargeTime, float attackSpeed, Supplier<Ingredient> repairMaterial) {
        this.durability = durability;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.baseChargeTime = baseChargeTime;
        this.attackSpeed = attackSpeed;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return durability;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
