package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import org.bukkit.Material;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class AstralLot extends IsolatedLot {

	protected double populationChance;

	public AstralLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ);

		this.populationChance = populationChance;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
									   PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
									   DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return blockYs.minHeight;
	}

	@Override
	public int getTopY(CityWorldGenerator generator, CachedYs blockYs, int x, int z) {
		return blockYs.maxHeight;
	}

	protected boolean getSuperSpecial() {
		return false;
	}

	private static int railOffset = 0;
	private static int specialOffset = 7;

	@Override
	public void generateMines(CityWorldGenerator generator, RealBlocks chunk) {
		int y = generator.seaLevel + AstralTownEmptyLot.aboveSeaLevel - 1;
		Shape shape = Shape.NORTH_SOUTH;

		// north/south along where the Nexus is
		if (chunk.getOriginX() == AstralNexusLot.blockX) {

			// underlayment
			for (int z = 0; z < 16; z++)
				generateOtherbits(chunk, railOffset, y, z, z == specialOffset);

			// now the rail itself
			// we do the following weirdness to ensure that power is properly recognized
			try {
				chunk.setDoPhysics(true);

				// from power source to end
				for (int z = specialOffset; z < 16; z++)
					chunk.setBlock(railOffset, y + 1, z, Material.POWERED_RAIL, shape, true);

				// from just before power source to start
				for (int z = specialOffset - 1; z >= 0; z--)
					chunk.setBlock(railOffset, y + 1, z, Material.POWERED_RAIL, shape, true);
			} finally {
				chunk.setDoPhysics(false);
			}
		}

		// west/east along where the Nexus is
		if (chunk.getOriginZ() == AstralNexusLot.blockZ) {
			shape = Shape.EAST_WEST;

			// underlayment
			for (int x = 0; x < 16; x++)
				generateOtherbits(chunk, x, y, railOffset, x == specialOffset);

			// now the rail itself
			// we do the following weirdness to ensure that power is properly recognized
			try {
				chunk.setDoPhysics(true);

				// from power source to end
				for (int x = specialOffset; x < 16; x++)
					chunk.setBlock(x, y + 1, railOffset, Material.POWERED_RAIL, shape, true);

				// from just before power source to start
				for (int x = specialOffset - 1; x >= 0; x--)
					chunk.setBlock(x, y + 1, railOffset, Material.POWERED_RAIL, shape, true);
			} finally {
				chunk.setDoPhysics(false);
			}
		}
	}

	private void generateOtherbits(RealBlocks chunk, int x, int y, int z, boolean specialPoint) {

		// underlayment
		chunk.setBlock(x, y, z, AstralTownEmptyLot.materialBase);
		if (specialPoint && !getSuperSpecial())
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlocks(x, blockYs.getBlockY(x, z), y, z, Material.QUARTZ_BLOCK);

		// need a tunnel?
		if (!chunk.isPartiallyEmpty(x, y + 2, y + 4, z)) {
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 1, Material.AIR);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x, y + 3, z, Material.AIR);
		}

		// power!
		if (specialPoint)
			chunk.setBlock(x, y, z, Material.REDSTONE_BLOCK);

	}
}
