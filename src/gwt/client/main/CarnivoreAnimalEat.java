package gwt.client.main;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

import java.util.Arrays;



public class CarnivoreAnimalEat extends WildAnimalEat{

	public CarnivoreAnimalEat() {
	
	}
	public CarnivoreAnimalEat(String... foods) {
		super();
		this.foods = Arrays.asList(foods);
	}
	
	@Override
	protected String getType() {
		return VConstants.livingbeing;
	}

}
