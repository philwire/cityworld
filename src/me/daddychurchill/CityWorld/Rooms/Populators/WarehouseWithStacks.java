package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageStacksRoom;
import org.bukkit.Material;

public class WarehouseWithStacks extends RoomProvider {

    public WarehouseWithStacks() {
        super();

        roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
        roomTypes.add(new StorageStacksRoom(Material.PISTON));
        roomTypes.add(new StorageStacksRoom(Material.CRAFTING_TABLE));
    }

}
