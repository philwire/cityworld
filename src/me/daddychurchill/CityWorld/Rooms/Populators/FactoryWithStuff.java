package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.*;
import org.bukkit.Material;

public class FactoryWithStuff extends RoomProvider {

	public FactoryWithStuff() {
		super();

		roomTypes.add(new EmptyRoom());
		roomTypes.add(new StorageSingleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageSingleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageSingleRowRoom(Material.PISTON));
		roomTypes.add(new StorageSingleRowRoom(Material.CRAFTING_TABLE));
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON));
		roomTypes.add(new StorageDoubleRowRoom(Material.CRAFTING_TABLE));
		roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Material.PISTON));
		roomTypes.add(new StorageStacksRoom(Material.CRAFTING_TABLE));
	}

}
