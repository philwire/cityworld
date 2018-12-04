package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public abstract class StorageTypeRoom extends StorageRoom {

    protected Material materialType;

    public StorageTypeRoom(Material type) {
        super();
        materialType = type;
    }

    protected void setStorageBlocks(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y1, int y2, int z) {
        switch (materialType) {
            case PISTON:
                chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType, BlockFace.UP);
                break;
            default:
                chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType);
                break;
        }
    }

}
