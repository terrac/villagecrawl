package gwt.client.main;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.person.Building;

import java.util.List;



public class MakeFood extends OObject{
	

	public void init(String[] pars) {
		
		
	}
	
	
	public Returnable execute(FullMapData map,LivingBeing person) {
		// move to stock
		Building house =(Building) map.get(VConstants.pithouse);
		if(house == null){
//			person.getTemplate().pending.add(new Structure());
			return new Returnable(false, 1);
		}
		
		addToList(person, new MakeFromStorageAndVerify(house,"storage","milk","cheese press",
				"cheese", "dining room"));
		addToList(person, new MakeFromStorageAndVerify(house,"storage","milk","butter churn",
				"butter", "dining room"));
		addToList(person, new MakeFromStorageAndVerify(house,"storage","wheat","oven",
				"bread", "dining room"));
		addToList(person, new MakeFromStorageAndVerify(house,"storage","eggs","oven",
				"boiled eggs", "dining room"));
		
		
		//make item (given an item requirements list it finds the nearest ingredients, collects thenm, and produces the item
		//after the given time segment
		
		//make food from stock
		//IE make butter,cheese from milk
		//make bread from wheat
		//kill and cook any meat
		//have a plan for food based on requirements so only enough is made for dinner, but nonperishables are created
		//from milk
		
		//additionally add a "Gathering" step that gathers various ingredients like mushrooms that grow naturally
		
		return new Returnable(false, 1);
	}

	
}
