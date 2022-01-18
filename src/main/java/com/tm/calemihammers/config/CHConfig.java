package com.tm.calemihammers.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class CHConfig {

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static final CategoryServer server = new CategoryServer(SERVER_BUILDER);

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

    public static class CategoryServer {

        public final ForgeConfigSpec.ConfigValue<Integer> maxVeinMineSize;

        public CategoryServer (ForgeConfigSpec.Builder builder) {
            maxVeinMineSize = builder.comment("Max Vein Mine Size", "The max amount of Blocks to break when charging a Sledgehammer.").defineInRange("maxVeinMineSize", 64, 0, 1024);
        }
    }
}