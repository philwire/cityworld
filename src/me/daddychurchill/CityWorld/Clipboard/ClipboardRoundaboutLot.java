package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.Support.PlatMap;
import org.bukkit.block.BlockFace;

public class ClipboardRoundaboutLot extends ClipboardRoadLot {

	public ClipboardRoundaboutLot(PlatMap platmap, int chunkX, int chunkZ, Clipboard clip, BlockFace facing, int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);

		style = LotStyle.ROUNDABOUT;
	}

}
