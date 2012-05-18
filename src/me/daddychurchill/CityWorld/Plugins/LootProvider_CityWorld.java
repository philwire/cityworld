package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.PlatMapContext;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootProvider_CityWorld extends LootProvider {

	public LootProvider_CityWorld() {

	}
	
	private int minTreasureId = Material.IRON_SPADE.getId();
	private int maxTreasureId = Material.ROTTEN_FLESH.getId();
	private int countTreasureIds = maxTreasureId - minTreasureId;

	@Override
	public void fill(Random rand, PlatMapContext context, String name, Block block) {

		if (name == "SewerVault") {
			// fabricate the SewerVault treasures
			
			int treasureCount = rand.nextInt(context.maxTreasureCount) + 1;
			ItemStack[] items = new ItemStack[treasureCount];
			for (int i = 0; i < treasureCount; i++) {
				items[i] = new ItemStack(rand.nextInt(countTreasureIds) + minTreasureId, rand.nextInt(2) + 1);
			}
			
			if (items.length > 0) {
				Chest chest = (Chest) block.getState();
				Inventory inv = chest.getInventory();
				inv.clear();
				inv.addItem(items);
			}
		}
		
	}
	

}
