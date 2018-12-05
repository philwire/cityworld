package me.daddychurchill.CityWorld.Context.Flooded;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedNatureLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.HeightInfo.HeightState;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedNatureContext extends NatureContext {

	public FloodedNatureContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createNaturalLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return new FloodedNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}

	@Override
	public PlatLot createSurfaceBuildingLot(CityWorldGenerator generator, PlatMap platmap, int x, int z, HeightInfo heights) {
		if (heights.averageHeight > generator.shapeProvider.findHighestFloodY(generator))
			return super.createSurfaceBuildingLot(generator, platmap, x, z, heights);
		return null;
	}

	@Override
	protected void populateSpecial(CityWorldGenerator generator, PlatMap platmap, int x, int y, int z, HeightState state) {
		if (y > generator.shapeProvider.findHighestFloodY(generator))
			super.populateSpecial(generator, platmap, x, y, z, state);
	}

}
