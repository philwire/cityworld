package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleRowRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Rooms.StorageEmptyChestsRoom;
import me.daddychurchill.CityWorld.Rooms.StorageFilledChestsRoom;
import org.bukkit.Material;

public class WarehouseWithRandom extends RoomProvider {

	public WarehouseWithRandom() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON));
		roomTypes.add(new StorageDoubleRowRoom(Material.CRAFTING_TABLE));
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageEmptyChestsRoom());
	}
}
