package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralEmptyLot extends AstralStructureLot {
	
	protected Material materialSupport = Material.COAL_BLOCK;
	protected Material materialCross = Material.STAINED_CLAY;
	protected Material materialBase = Material.QUARTZ_BLOCK;
	protected int aboveSeaLevel = 5;

	public AstralEmptyLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context,
			int platX, int platZ) {
		
		int levelY = generator.seaLevel + aboveSeaLevel;
		
//		chunk.setBlocks(7, 9, blockYs.minHeight - 4, levelY - 1, 7, 9, materialSupport);
		chunk.setBlocks(0, blockYs.minHeight - 4, levelY - 1, 0, materialSupport);
		chunk.setBlocks(0, blockYs.minHeight - 4, levelY - 1, 15, materialSupport);
		chunk.setBlocks(15, blockYs.minHeight - 4, levelY - 1, 0, materialSupport);
		chunk.setBlocks(15, blockYs.minHeight - 4, levelY - 1, 15, materialSupport);

		chunk.setBlocks(1, levelY - 4, levelY - 1, 1, materialSupport);
		chunk.setBlocks(1, levelY - 4, levelY - 1, 14, materialSupport);
		chunk.setBlocks(14, levelY - 4, levelY - 1, 1, materialSupport);
		chunk.setBlocks(14, levelY - 4, levelY - 1, 14, materialSupport);

		chunk.setBlocks(1, 15, levelY - 3, levelY - 1, 0, 1, materialCross);
		chunk.setBlocks(15, 16, levelY - 3, levelY - 1, 1, 15, materialCross);
		chunk.setBlocks(1, 15, levelY - 3, levelY - 1, 15, 16, materialCross);
		chunk.setBlocks(0, 1, levelY - 3, levelY - 1, 1, 15, materialCross);
		
		chunk.setBlocks(0, 16, levelY - 1, levelY, 0, 16, materialBase);
	}

}
