package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.Context.PlatMapContext;

import com.codisimus.plugins.phatloots.PhatLoots;
import com.codisimus.plugins.phatloots.PhatLoot;
import com.codisimus.plugins.phatloots.PhatLootChest;

public class LootProvider_PhatLoots extends LootProvider {
	
	@Override
	public void fill(Random rand, PlatMapContext context, String name, Block block) {

		//https://github.com/Codisimus/PhatLoots
		
		PhatLoot phatLoot;
		if (!PhatLoots.hasPhatLoot(name)) {
        	PhatLoots.addPhatLoot(new PhatLoot(name));        	
        }
		phatLoot = PhatLoots.getPhatLoot(name);
		
		phatLoot.chests.add(new PhatLootChest(block));
		phatLoot.save();
		
	}

	private static String name = "PhatLoots";
	public static LootProvider loadPhatLoots() {

		PhatLoots phatLoots = null;

		PluginManager pm = Bukkit.getServer().getPluginManager();

		try {
			phatLoots = (PhatLoots) pm.getPlugin(name);
		} catch (Exception ex) {
			CityWorld.log.info(String.format("[CityWorld][LootProvider] Bad Version %s.", name));
		}

		if (phatLoots == null)
			return null;

		CityWorld.log.info(String.format("[CityWorld][LootProvider] Found %s.", name));
		
		try {

			if (!pm.isPluginEnabled(phatLoots)) {
				CityWorld.log.info(String.format("[CityWorld][LootProvider] Enabling %s.", name));
				pm.enablePlugin(phatLoots);
			}
			CityWorld.log.info(String.format("[CityWorld][LootProvider] %s Enabled.", name));
			
			return new LootProvider_PhatLoots();
			
		} catch (Exception ex) {
			CityWorld.log.info(String.format("[CityWorld][LootProvider] Failed to enable %s.", name));
			CityWorld.log.info(ex.getStackTrace().toString());
			return null;
		}

	}
}
