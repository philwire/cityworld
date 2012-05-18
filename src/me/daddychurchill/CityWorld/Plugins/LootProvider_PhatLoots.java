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
	
	private LootProvider_PhatLoots(PhatLoots plugin) {
		super();
	}
	
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

		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		PhatLoots phatLoots;
		phatLoots = (PhatLoots) pm.getPlugin(name);

		if (phatLoots == null) {
			return null;
		} else {

			CityWorld.log.info(String.format("[CityWorld][LootProvider] Found %s.", name));
			
			try {

				if (!pm.isPluginEnabled(phatLoots)) {
					CityWorld.log.info(String.format("[CityWorld][LootProvider] Enabling %s.", name));
					pm.enablePlugin(phatLoots);
				}
				CityWorld.log.info(String.format("[CityWorld][LootProvider] %s Enabled.", name));
				
				return new LootProvider_PhatLoots(phatLoots);
				
			} catch (Exception ex) {
				
				CityWorld.log.info(String.format("[CityWorld][LootProvider] Failed to enable %s.", name));
				CityWorld.log.info(ex.getStackTrace().toString());
				return null;
			}
			
		}
		
	}

}
