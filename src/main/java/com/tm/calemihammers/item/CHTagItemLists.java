package com.tm.calemihammers.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CHTagItemLists {

    public static final List<Item> logTags = new ArrayList<>();
    public static final List<Item> oreTags = new ArrayList<>();

    public static boolean isLog(Block block) {

        if (logTags.isEmpty()) {
            logTags.addAll(ItemTags.LOGS.getValues());
        }

        return Objects.requireNonNull(CHTagItemLists.logTags).contains(block.asItem());
    }

    public static boolean isOre(Block block) {

        if (oreTags.isEmpty()) {
            oreTags.addAll(ItemTags.getAllTags().getTag(new ResourceLocation(ForgeMod.getInstance().getModId(), "ores")).getValues());
            oreTags.addAll(ItemTags.COAL_ORES.getValues());
            oreTags.addAll(ItemTags.DIAMOND_ORES.getValues());
            oreTags.addAll(ItemTags.COPPER_ORES.getValues());
            oreTags.addAll(ItemTags.EMERALD_ORES.getValues());
            oreTags.addAll(ItemTags.REDSTONE_ORES.getValues());
            oreTags.addAll(ItemTags.LAPIS_ORES.getValues());
            oreTags.addAll(ItemTags.GOLD_ORES.getValues());
            oreTags.addAll(ItemTags.IRON_ORES.getValues());
        }

        return Objects.requireNonNull(CHTagItemLists.oreTags).contains(block.asItem());
    }
}
