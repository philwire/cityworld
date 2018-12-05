package me.daddychurchill.CityWorld.Plats.Maze;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import org.bukkit.Material;

public class MazeWaterWalledLot extends MazeLavaWalledLot {

	public MazeWaterWalledLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Material getWallMaterial(CityWorldGenerator generator) {
		return Material.WATER;
	}

}
