package com.tm.calemicore.util.helper;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Use this class for to help with Inventories.
 */
public class InventoryHelper {

    /**
     * @param inventory The Inventory to count in.
     * @param stack The type of Item Stack to count.
     * @param exactNBT Should it only count the same NBT items?
     * @return The amount of a specified Item Stack in an Inventory.
     */
    public static int countItems(Inventory inventory, ItemStack stack, boolean exactNBT) {

        if (stack.getCount() > stack.getMaxStackSize()) {

            for (int i = 0; i < stack.getCount(); i++) {
                stack.setCount(1);
                countItems(inventory, stack, exactNBT);
            }
        }

        int count = 0;

        for (int slotId = 0; slotId < inventory.getContainerSize(); slotId++) {

            ItemStack stackInSlot = inventory.getItem(slotId);

            if (stackInSlot.sameItem(stack)) {

                if (exactNBT && stack.hasTag()) {

                    if (stackInSlot.hasTag() && Objects.equals(stackInSlot.getTag(), stack.getTag())) {
                        count += stackInSlot.getCount();
                    }
                }

                else count += stackInSlot.getCount();
            }
        }

        return count;
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param inventory The Inventory to remove from.
     * @param stack The type of Item Stack to remove.
     * @param amount The amount to remove.
     * @param exactNBT Should it only remove the same NBT items?
     */
    public static void consumeItems(Inventory inventory, ItemStack stack, int amount, boolean exactNBT) {

        int amountLeft = amount;

        if (countItems(inventory, stack, exactNBT) >= amount) {

            for (int slotId = 0; slotId < inventory.getContainerSize(); slotId++) {

                if (amountLeft <= 0) {
                    break;
                }

                ItemStack stackInSlot = inventory.getItem(slotId);

                if (!stackInSlot.isEmpty()) {

                    if (stackInSlot.sameItem(stack)) {

                        if (exactNBT && stack.hasTag()) {

                            if (!stackInSlot.hasTag() || !Objects.equals(stackInSlot.getTag(), stack.getTag())) {
                                continue;
                            }
                        }

                        if (amountLeft >= stackInSlot.getCount()) {
                            amountLeft -= stackInSlot.getCount();
                            inventory.setItem(slotId, ItemStack.EMPTY);
                        }

                        else {
                            ItemStack copy = stackInSlot.copy();
                            inventory.removeItem(slotId, amountLeft);
                            amountLeft -= copy.getCount();
                        }
                    }
                }
            }
        }
    }
}
