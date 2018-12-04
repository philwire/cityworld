package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import org.bukkit.Material;

public class OreProvider_SandDunes extends OreProvider_Normal {

    public OreProvider_SandDunes(CityWorldGenerator generator) {
        super(generator);

        fluidMaterial = Material.SAND;
        fluidFluidMaterial = Material.SAND;
        fluidSurfaceMaterial = Material.SAND;
        fluidSubsurfaceMaterial = Material.SANDSTONE;
        fluidFrozenMaterial = Material.SANDSTONE;
    }
}
