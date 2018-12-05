package me.daddychurchill.CityWorld.Plats.Nature;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.*;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class MineEntranceLot extends ConstructLot {

	public MineEntranceLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MineEntranceLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return 0;
	}

	@Override
	public int getTopY(CityWorldGenerator generator, CachedYs blockYs, int x, int z) {
		return generator.streetLevel + DataContext.FloorHeight;
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		reportLocation(generator, "Mine Entrance", chunk);

		// find the bottom of the world
		int shaftY = findHighestShaftableLevel(generator, context, chunk);
		shaftY = Math.max(2, shaftY); // make sure we don't go down too far

		// where is the surface?
		int surfaceY = Math.min(getBlockY(0, 0), getBlockY(3, 0));
		surfaceY = Math.min(surfaceY, getBlockY(0, 3));
		surfaceY = Math.min(surfaceY, getBlockY(3, 3));

		// core bits
		Material center = Material.AIR;
		switch (chunkOdds.getRandomInt(6)) {
			case 1:
				center = Material.IRON_BARS;
				break;
			case 2:
				center = Material.SPRUCE_FENCE;
				break;
			case 3:
				center = Material.COBBLESTONE;
				break;
			default:
				// else air will do
				break;
		}

		// do it!
		generateStairWell(generator, chunk, chunkOdds, 0, 0, shaftY,
			blockYs.minHeight, surfaceY, surfaceY + DataContext.FloorHeight + 1,
			Material.COBBLESTONE_STAIRS, Material.COBBLESTONE, center);

		// connect to the minecraft
		chunk.setBlocks(0, 4, shaftY - 1, 0, 4, Material.COBBLESTONE);
		chunk.setBlocks(2, 4, shaftY - 1, 4, 6, Material.COBBLESTONE);
		chunk.setBlocks(2, 4, shaftY, shaftY + 2, 4, 6, Material.AIR);

		// place snow
		generateSurface(generator, chunk, false);
	}

	private final static double oddsOfStairs = Odds.oddsVeryLikely;
	private final static double oddsOfLanding = Odds.oddsVeryLikely;

	public static void generateStairWell(CityWorldGenerator generator, SupportBlocks chunk, Odds odds,
										 int offX, int offZ, int shaftY, int minHeight, int surfaceY, int clearToY,
										 Material stairs, Material landing, Material center) {

		// drill down
		chunk.setBlocks(offX, offX + 4, shaftY, clearToY, offZ, offZ + 4, Material.AIR);
		chunk.setBlocks(offX + 1, offX + 3, shaftY, surfaceY, offZ + 1, offZ + 3, center);

		// make the surface bits
		chunk.setBlocks(offX, offX + 4, minHeight, surfaceY + 1, offZ, offZ + 4, landing);

		// now do the stair
		do {
			shaftY = generateStairs(generator, chunk, odds, offX + 3, shaftY, offZ + 2,
				BlockFace.NORTH, BlockFace.SOUTH, stairs);
			if (shaftY > surfaceY)
				break;
			shaftY = generateStairs(generator, chunk, odds, offX + 3, shaftY, offZ + 1,
				BlockFace.NORTH, BlockFace.EAST, stairs);
			if (shaftY > surfaceY)
				break;
			generateLanding(generator, chunk, odds, offX + 3, shaftY, offZ,
				BlockFace.EAST, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX + 2, shaftY, offZ,
				BlockFace.WEST, BlockFace.EAST, stairs);
			if (shaftY > surfaceY)
				break;
			shaftY = generateStairs(generator, chunk, odds, offX + 1, shaftY, offZ,
				BlockFace.WEST, BlockFace.NORTH, stairs);
			if (shaftY > surfaceY)
				break;
			generateLanding(generator, chunk, odds, offX, shaftY, offZ,
				BlockFace.NORTH, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX, shaftY, offZ + 1,
				BlockFace.SOUTH, BlockFace.NORTH, stairs);
			if (shaftY > surfaceY)
				break;
			shaftY = generateStairs(generator, chunk, odds, offX, shaftY, offZ + 2,
				BlockFace.SOUTH, BlockFace.WEST, stairs);
			if (shaftY > surfaceY)
				break;
			generateLanding(generator, chunk, odds, offX, shaftY, offZ + 3,
				BlockFace.WEST, stairs, landing);

			shaftY = generateStairs(generator, chunk, odds, offX + 1, shaftY, offZ + 3,
				BlockFace.EAST, BlockFace.WEST, stairs);
			if (shaftY > surfaceY)
				break;
			shaftY = generateStairs(generator, chunk, odds, offX + 2, shaftY, offZ + 3,
				BlockFace.EAST, BlockFace.SOUTH, stairs);
			if (shaftY > surfaceY)
				break;
			generateLanding(generator, chunk, odds, offX + 3, shaftY, offZ + 3,
				BlockFace.SOUTH, stairs, landing);
		} while (shaftY <= surfaceY);

//		//TODO remove this flag!
//		chunk.setBlocks(2, surfaceY + 5, maxHeight + 20, 1, Material.GLOWSTONE);
	}

	public static int generateStairs(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z,
									 BlockFace direction, BlockFace underdirection, Material stairs) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);

		// make a step... or not...
		if (!generator.settings.includeDecayedBuildings || odds.playOdds(oddsOfStairs)) {
			chunk.setBlock(x, y, z, stairs, direction);
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, stairs, underdirection, Half.TOP);
		}

		// moving on up
		y++;

		// far enough?
		return y;
	}

	public static void generateLanding(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z,
									   BlockFace underdirection, Material stairs, Material landing) {
		chunk.setBlocks(x, y, y + 3, z, Material.AIR);

		// make a landing... or not...
		if (!generator.settings.includeDecayedBuildings || odds.playOdds(oddsOfLanding)) {
			chunk.setBlock(x, y - 1, z, landing);
			if (chunk.isEmpty(x, y - 2, z))
				chunk.setBlock(x, y - 2, z, stairs, underdirection, Half.TOP);
		}
	}
}
