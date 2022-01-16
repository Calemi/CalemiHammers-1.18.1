package com.tm.calemicore.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.extensions.IForgeBlockState;

import java.util.ArrayList;

/**
 * Used to scan through blocks of the same kind. Collects all scanned blocks in a list.
 */
public class BlockScanner {

    public final ArrayList<Location> buffer = new ArrayList<>();

    private final Location origin;
    private final IForgeBlockState blockToScan;
    private final int maxScanSize;

    public BlockScanner(Location location, IForgeBlockState blockToScan, int maxScanSize) {
        this.origin = location;
        this.blockToScan = blockToScan;
        this.maxScanSize = maxScanSize;
    }

    public void startVeinScan () {
        reset();
        scan(origin, false);
    }

    public void startRadiusScan () {
        reset();
        scan(origin, true);
    }

    public void reset () {
        buffer.clear();
    }

    private void scan (Location location, boolean radiusBranch) {

        if (buffer.size() >= maxScanSize) {
            return;
        }

        if (!buffer.contains(location) && location.getBlock() != null) {

            if (location.getBlockState() != blockToScan) {
                return;
            }

            buffer.add(location);

            if (radiusBranch) {

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {

                            Location nextLocation = new Location(location.level, location.x + x, location.y + y, location.z + z);
                            scan(nextLocation, radiusBranch);
                        }
                    }
                }
            }

            else {

                for (Direction dir : Direction.values()) {
                    scan(new Location(location, dir), radiusBranch);
                }
            }
        }
    }

    private boolean contains (Location location) {

        for (Location nextLocation : buffer) {

            if (nextLocation.equals(location)) {
                return true;
            }
        }

        return false;
    }
}
