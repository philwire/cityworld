package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import org.bukkit.block.BlockFace;

public class ClipboardRoadLot extends ClipboardLot {

    public ClipboardRoadLot(PlatMap platmap, int chunkX, int chunkZ, Clipboard clip, BlockFace facing, int lotX, int lotZ) {
        super(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);

        style = LotStyle.ROAD;
    }

    @Override
    public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
        return generator.settings.inRoadRange(chunkX, chunkZ);
    }
}
