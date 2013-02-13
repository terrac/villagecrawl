package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class Pottery extends OObjectB {

	
	public static final String cultureSpecific = "culturespecificpottery";

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		addToList(person, new MoveClosestAndCreateNearbyIfOccupied(VConstants.gate,"kiln",40));
		String pot = "pot";
		String cpot = (String) person.getGroup().getStatic(cultureSpecific);
		if(cpot != null){
			pot = cpot;
		} 
		//addToList(person, new MakeItem(person.getItemsMap().get(pot)));
		
		//go to narest kiln
		
		//if kiln is occupied and there are no other kilns within a radius of 10
		//then create
		
		//if kiln not created create it
		//make item (item waits appropriate turns
		

		return null;
	}

}
