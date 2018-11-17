package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class DeskCubbyRoom extends DeskRoom {

    public DeskCubbyRoom() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
                            int y, int z, int width, int height, int depth,
                            BlockFace sideWithWall, Material materialWall, Material materialGlass) {
        Material tableLeg = getTableLeg(odds);
        Material tableTop = getTableTop(odds);

        switch (sideWithWall) {
            default:
            case NORTH:
                chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
                chunk.setBlocks(x + 1, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
                chunk.setTable(x + 1, x + 2, y, z, z + 2, tableLeg, tableTop);
                chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);
                break;
            case SOUTH:
                chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
                chunk.setBlocks(x, x + width - 1, y, y + height, z, z + 1, materialWall);
                chunk.setTable(x + 1, x + 2, y, z + 1, z + 3, tableLeg, tableTop);
                chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
                break;
            case WEST:
                chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
                chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth - 1, materialWall);
                chunk.setTable(x, x + 2, y, z + 1, z + 2, tableLeg, tableTop);
                chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
                break;
            case EAST:
                chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
                chunk.setBlocks(x, x + 1, y, y + height, z + 1, z + depth, materialWall);
                chunk.setTable(x + 1, x + 3, y, z + 1, z + 2, tableLeg, tableTop);
                chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
                break;
        }
    }

}
