package gwt.client.main.base.under;

import gwt.client.main.VConstants;
import gwt.client.map.AgeMapData;
import gwt.client.map.Direction;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.PhysicalMapData;

public class Fish extends FoodGroup{

	public Fish(String type) {
		super();
		this.type = type;
	}
	public Fish(String type, int health) {
		super();
		this.type = type;
		this.health = health;
	}

	@Override
	public Fish clone() {
		
		return new Fish(type);
	}
	public Fish() {
		type ="fish";
	}
	@Override
	public String getValue() {
		if( health < 50){
			return "half"+type;
		}
		return type;
	}
	
	@Override
	public String getUnder() {
		
		return VConstants.water;
	}
	}
