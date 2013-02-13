package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;

public class Gathering extends OObject {
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		Plant plant=(Plant) person.getParent().get(VConstants.foodgroup);
		if(plant == null){
			return null;
		}
		Item md = person.getItemsMap().get(plant.type).clone();
		if(md == null){
			return null;
		}
		plant.addHealth(-80);
		
		person.getItems().put(md);
		System.out.println(person);
		return null;
	}

}
