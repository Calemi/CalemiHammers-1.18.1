package com.tm.calemicore.util.helper;

import com.tm.calemicore.util.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/**
 * Used to help with placing Blocks.
 */
public class BlockHelper {

    //The maximum size of Blocks to search in a row.
    private static final int MAX_ROW_SIZE = 64;

    /**
     * Places a block in the next available place in a row.
     * @param block The Block that gets placed.
     * @param originPos The origin of the search.
     * @param rowDirection The Direction of the row.
     */
    public static void placeBlockRow(Level level, Player player, Block block, BlockPos originPos, Direction rowDirection) {

        Location location = new Location(level, originPos);
        ItemStack currentStack = player.getMainHandItem();

        //Checks if the held stack is a Block.
        if (currentStack.getItem() == Item.BY_BLOCK.get(block)) {

            //Iterates through every possible placement in a line.
            for (int i = 0; i < MAX_ROW_SIZE; i++) {

                Location nextLocation = new Location(location, rowDirection, i);

                //Checks if a different Block/AIR has been found.
                if (nextLocation.getBlock() != block) {

                    //Checks if the Block be placed here.
                    if (nextLocation.isBlockValidForPlacing()) {

                        nextLocation.setBlock(block);
                        SoundHelper.playBlockPlace( nextLocation, nextLocation.getBlockState());
                        InventoryHelper.consumeItems(player.getInventory(), new ItemStack(block), 1, false);
                    }

                    break;
                }
            }
        }
    }

    public static boolean canPlaceTorchAt (Location location) {

        if (!location.isBlockValidForPlacing()) {
            return false;
        }

        if (location.getLightValue() > 7) {
            return false;
        }

        if (location.level.isWaterAt(location.getBlockPos())) {
            return false;
        }

        Location locationDown = new Location(location, Direction.DOWN);

        return locationDown.isFullCube();
    }
}
