package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class DebugRoom extends PlatRoom {

	public DebugRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
							int y, int z, int width, int height, int depth,
							BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		chunk.setBlocks(x, x + width, y, y + 1, z, z + depth, materialWall);
		switch (sideWithWall) {
			default:
			case NORTH:
				chunk.setBlocks(x, x + width, y + 1, y + height, z, z + 1, Material.EMERALD_BLOCK);
				break;
			case SOUTH:
				chunk.setBlocks(x, x + width, y + 1, y + height, z + depth - 1, z + depth, Material.EMERALD_BLOCK);
				break;
			case WEST:
				chunk.setBlocks(x, x + 1, y + 1, y + height, z, z + depth, Material.EMERALD_BLOCK);
				break;
			case EAST:
				chunk.setBlocks(x + width - 1, x + width, y + 1, y + height, z, z + depth, Material.EMERALD_BLOCK);
				break;
		}
	}

}
