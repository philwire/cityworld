package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.*;

public class BusinessWithCafe extends RoomProvider {

	public BusinessWithCafe() {
		super();

		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());

		roomTypes.add(new LoungeKitchenetteRoom());

		// two wide table with chair and a book stand (with possible flower pot)
	}

}
