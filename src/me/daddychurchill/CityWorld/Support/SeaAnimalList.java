package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public class SeaAnimalList extends EntityList {

	public SeaAnimalList(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SeaAnimalList(String name, EntityType... entities) {
		super(name, entities);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getHerdSize(Odds odds, EntityType entity) {
		switch (entity) {
			default:
				return odds.getRandomInt(3, 6);
			case SQUID:
				return odds.getRandomInt(1, 3);
			case GUARDIAN:
			case ELDER_GUARDIAN:
				return 1;
		}
	}
}
