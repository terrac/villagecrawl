package gwt.client.main;

import gwt.client.astar.world.Mover;
import gwt.client.item.Item;
import gwt.client.main.base.Body;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HMapData;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;

public class Person extends LivingBeing implements Mover {

	public Person() {

	}

	public Person(String name) {

		setType(name);

		// put("body", new Body(new Stats()));
		setAge(356 * 18);

		// template.registerStatistics(this);
	}

	public Person(String name, Stats stats) {
		super(name, stats);
	}

	public Person(Person p) {
		this("baby");
		setAge(0);
		getTemplate().setRationalChild("aoeu", "baby");
	}

	// {
	// map.put("property", new PropertyMap());
	//	
	// }

	// TreeMap prev;

	public int getHumanAge() {
		return (int) (getAge() / 356);
	}

	@Override
	public boolean age() {
		int i = getAge() / 356;

		// if(i ==14){
		// template.setDominantChild(getBody().getStats().getSex()+"adult");
		// }
		// if(i ==3){
		// template.setDominantChild("youngchild");
		// }
		return super.age();
	}

	@Override
	public boolean isPerson() {
		return true;

	}

	@Override
	public Person clone() {
		return new Person().copyDeepProperties(this);
	}
	// @Override
	// public String toString() {
	//		
	// return super.toString()+"\n"+getBody().getItems();
	// }

}
