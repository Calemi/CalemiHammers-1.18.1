package com.tm.calemihammers.tab;

import com.tm.calemihammers.init.InitItems;
import com.tm.calemihammers.main.CHReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CHTab extends CreativeModeTab {

    public CHTab() {
        super(CHReference.MOD_ID + ".tabMain");
    }

    @Override
    public ItemStack makeIcon () {
        return new ItemStack(InitItems.SLEDGEHAMMER_STARLIGHT.get());
    }
}
