package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class LoungeEllCouchRoom extends LoungeCouchRoom {

    public LoungeEllCouchRoom() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
                            int y, int z, int width, int height, int depth,
                            BlockFace sideWithWall, Material materialWall, Material materialGlass) {

        super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth,
                sideWithWall, materialWall, materialGlass);

        Material tableLeg = getTableLeg(odds);
        Material tableTop = getTableTop(odds);

        switch (sideWithWall) {
            default:
            case NORTH:
                for (int z1 = z + 1; z1 < z + depth; z1++)
                    chunk.setBlock(x + width - 1, y, z1, Material.BIRCH_STAIRS, BlockFace.EAST);
                chunk.setTable(x, x + width - 2, y, z + depth - 1, z + depth, tableLeg, tableTop);
                break;
            case SOUTH:
                for (int z1 = z; z1 < z + depth - 1; z1++)
                    chunk.setBlock(x, y, z1, Material.BIRCH_STAIRS, BlockFace.WEST);
                chunk.setTable(x + 2, x + width, y, z, z + 1, tableLeg, tableTop);
                break;
            case WEST:
                for (int x1 = x + 1; x1 < x + width; x1++)
                    chunk.setBlock(x1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
                chunk.setTable(x + 2, x + width, y, z + depth - 1, z + depth, tableLeg, tableTop);
                break;
            case EAST:
                for (int x1 = x; x1 < x + width - 1; x1++)
                    chunk.setBlock(x1, y, z + depth - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);
                chunk.setTable(x, x + width - 2, y, z, z + 1, tableLeg, tableTop);
                break;
        }
    }

}
