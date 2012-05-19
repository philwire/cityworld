package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.block.Block;

import me.daddychurchill.CityWorld.Context.PlatMapContext;

public abstract class LootProvider {

	protected LootProvider() {
		// who's your daddy?
	}
	
	public abstract void fill(Random rand, PlatMapContext context, String name, Block block);

	public static LootProvider loadLootProvider() {

		LootProvider provider;
		
		// try PhatLoots
		provider = LootProvider_PhatLoots.loadPhatLoots();
		
		// default to stock LootProvider
		if (provider == null) {
			provider = new LootProvider_CityWorld();
		}
	
		return provider;
	
	}
	
}
