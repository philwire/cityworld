package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.block.Block;

import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Context.PlatMapContext;

public class LootContext {

	private Random rand;
	private String name;
	private LootProvider provider;
	private PlatMapContext context;

	// world?
	
	public LootContext(Random rand, LootProvider provider, PlatMapContext context, String name) {
		this.rand=rand;
		this.provider=provider;
		this.context=context;
		this.name=name;
	}

	public void fill(Block block) {
		provider.fill(rand, context, name, block);
	}

}
