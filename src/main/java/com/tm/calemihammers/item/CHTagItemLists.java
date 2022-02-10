package com.tm.calemihammers.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CHTagItemLists {

    public static final List<Item> logTags = new ArrayList<>();
    public static final List<Item> oreTags = new ArrayList<>();

    public static boolean isLog(Block block) {
        return Objects.requireNonNull(CHTagItemLists.logTags).contains(block.asItem());
    }

    public static boolean isOre(Block block) {
        return Objects.requireNonNull(CHTagItemLists.oreTags).contains(block.asItem());
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {

        CHTagItemLists.logTags.clear();
        CHTagItemLists.oreTags.clear();

        CHTagItemLists.logTags.addAll(ItemTags.LOGS.getValues());

        CHTagItemLists.oreTags.addAll(ItemTags.getAllTags().getTag(new ResourceLocation(ForgeVersion.MOD_ID, "ores")).getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.COAL_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.DIAMOND_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.COPPER_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.EMERALD_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.REDSTONE_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.LAPIS_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.GOLD_ORES.getValues());
        CHTagItemLists.oreTags.addAll(ItemTags.IRON_ORES.getValues());
    }
}
