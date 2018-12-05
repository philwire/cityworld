package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBlackCubesLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBlackTowerLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import org.bukkit.Material;

public class AstralBlackZoneContext extends AstralDataContext {

	public AstralBlackZoneContext(CityWorldGenerator generator) {
		super(generator);

	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {

		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);

		// random fluff
		ShapeProvider shapeProvider = generator.shapeProvider;

		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		boolean towerPlaced = false;

		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {

					// what is the world location of the lot?
					int blockX = (originX + x) * SupportBlocks.sectionBlockWidth;
					int blockZ = (originZ + z) * SupportBlocks.sectionBlockWidth;

					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.anyEmpties && heights.averageHeight < generator.seaLevel) {

						// tower?
						if (!towerPlaced && shapeProvider.isIsolatedConstructAt(originX + x, originZ + z, oddsOfIsolatedConstructs)) {
							current = new AstralBlackTowerLot(platmap, originX + x, originZ + z);
							towerPlaced = true;
						}

						// still empty? well then lets put some blocks then
						if (current == null)
							current = new AstralBlackCubesLot(platmap, originX + x, originZ + z, getPopulationOdds(x, z));
					}

					// did current get defined?
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Material getMapRepresentation() {
		return Material.OBSIDIAN;
	}
}
