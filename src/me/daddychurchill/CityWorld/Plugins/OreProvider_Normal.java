package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import org.bukkit.Material;

import java.util.ArrayList;

public class OreProvider_Normal extends OreProvider {

	public OreProvider_Normal(CityWorldGenerator generator) {
		super(generator);

		ore_types = new ArrayList<>();
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(0, Material.WATER));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(1, Material.LAVA));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(2, Material.GRAVEL));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(3, Material.COAL_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(4, Material.IRON_ORE));

		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(5, Material.GOLD_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(6, Material.LAPIS_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(7, Material.REDSTONE_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(8, Material.DIAMOND_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NormalOres.getNthMaterial(9, Material.EMERALD_ORE));
	}
}
