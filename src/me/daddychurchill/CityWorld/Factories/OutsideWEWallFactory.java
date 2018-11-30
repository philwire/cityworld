package me.daddychurchill.CityWorld.Factories;

import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class OutsideWEWallFactory extends MaterialFactory {

    public OutsideWEWallFactory(Odds odds, boolean decayed) {
        super(odds, decayed);
    }

    public OutsideWEWallFactory(MaterialFactory other) {
        super(other);
    }

    @Override
    public void placeMaterial(AbstractBlocks blocks, Material primary, Material secondary, int x, int y1, int y2, int z, BlockFace... facing) {
        super.placeMaterial(blocks, primary, secondary, pickMaterial(primary, secondary, x), x, y1, y2, z, facing);
    }
}
