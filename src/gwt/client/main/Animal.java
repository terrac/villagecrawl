package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;


public class Animal extends LivingBeing{

	public Animal() {
		super();
	}

	public Animal(LivingBeing p, Stats stats) {
		super(p, stats);
	}

	public Animal(String name,  Stats stats
			) {
		super(name, stats);
	}
	
	@Override
	public Animal clone() {
		return new Animal().copyDeepProperties(this);
	}

}
