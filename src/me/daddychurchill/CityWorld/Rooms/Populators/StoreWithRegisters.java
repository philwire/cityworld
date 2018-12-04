package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.RegisterRoom;
import me.daddychurchill.CityWorld.Rooms.StorageSingleRowRoom;
import org.bukkit.Material;

public class StoreWithRegisters extends RoomProvider {

    public StoreWithRegisters() {
        super();

        roomTypes.add(new StorageSingleRowRoom(Material.BOOKSHELF));
        roomTypes.add(new EmptyRoom());
        roomTypes.add(new EmptyRoom());
        roomTypes.add(new RegisterRoom());
        roomTypes.add(new RegisterRoom());
    }

}
