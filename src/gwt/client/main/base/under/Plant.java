package gwt.client.main.base.under;

import gwt.client.main.VConstants;
import gwt.client.map.AgeMapData;
import gwt.client.map.Direction;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.PhysicalMapData;

public class Plant extends FoodGroup{

	public Plant(String type) {
		super();
		this.type = type;
	}
	public Plant(String type, int health) {
		super();
		this.type = type;
		this.health = health;
	}

	@Override
	public Plant clone() {
		
		return new Plant(type);
	}
	public Plant() {
	}
	@Override
	public String getValue() {
		if( health < 50){
			return "half"+type;
		}
		return type;
	}
	
	}
