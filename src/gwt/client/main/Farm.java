package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;


public class Farm extends OObject{
	String type;
	
	public void init(String[] pars) {
		//this.type = pars[0];
		
		
	}
	
	public Returnable execute(FullMapData map, LivingBeing person) {
		
		

			//execute a farming task this maintains the age and the quality of the
			//farm.  If it is harvest time then a lot of wheat is produced
			//otherwise milk and occasionally meat is produced.  Meat is random
			//and it degrades the quality of the farm if there aren't enough
			//male cows,sheep, or whatever

			
			//lower age of farm
			
			//if time of year is october then produce
			//wheat.  Put as an item on the farm (in a physical location as well) and put in property of person		

			//if farm has mammals then produce milk (do same with milk)

		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		if(house == null){
			//should now be done in init
			//person.getTemplate().pending.add(new Structure());
			return new Returnable(true, 1);
		}
		
		//Log.log("a",person+" "+house);
		HashMapData to = (HashMapData) house.get("cow");
		if(to == null){
			return new Returnable();
		}
//		person.getTemplate().pending.add(new Move(to,"movecow"));
//		person.getTemplate().pending.add(new MakeItem(person.getItemsMap().get("milk")));
//		person.getTemplate().pending.add(new Carry("milk",(HashMapData) house.get("storage")));
//		person.getTemplate().pending.add(new Move((HashMapData) house.get("chicken"),"movechicken"));
//		person.getTemplate().pending.add(new MakeItem(person.getItemsMap().get("eggs")));
//		person.getTemplate().pending.add(new Carry("eggs",(HashMapData) house.get("storage")));
//		
		
		return new Returnable(false, 0);
	}
	
	
	
	

}
