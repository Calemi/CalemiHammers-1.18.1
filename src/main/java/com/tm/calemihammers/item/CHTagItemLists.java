package com.tm.calemihammers.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

        addItemsFromTag(logTags, ItemTags.LOGS);

        Stream<Pair<TagKey<Item>, HolderSet.Named<Item>>> tags = Registry.ITEM.getTags();

        for (Pair<TagKey<Item>, HolderSet.Named<Item>> tag : tags.toList()) {

            if (tag.getFirst().location().equals(new ResourceLocation(ForgeVersion.MOD_ID, "ores"))) {
                addItemsFromTag(oreTags, tag.getFirst());
            }
        }

        addItemsFromTag(oreTags, ItemTags.COAL_ORES);
        addItemsFromTag(oreTags, ItemTags.DIAMOND_ORES);
        addItemsFromTag(oreTags, ItemTags.COPPER_ORES);
        addItemsFromTag(oreTags, ItemTags.EMERALD_ORES);
        addItemsFromTag(oreTags, ItemTags.REDSTONE_ORES);
        addItemsFromTag(oreTags, ItemTags.LAPIS_ORES);
        addItemsFromTag(oreTags, ItemTags.GOLD_ORES);
        addItemsFromTag(oreTags, ItemTags.IRON_ORES);
    }

    private static void addItemsFromTag(List<Item> list, TagKey<Item> tag) {

        for (Holder<Item> holder : Registry.ITEM.getTagOrEmpty(tag)) {
            list.add(holder.value());
        }
    }
}
