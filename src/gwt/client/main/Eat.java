package gwt.client.main;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.person.Building;

import java.util.List;



public class Eat extends OObject{
	

	public void init(String[] pars) {
		
		
	}
	
	
	public Returnable execute(FullMapData map,LivingBeing person) {
		// move to stock
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		
		
		person.registerDecision(1,VConstants.food,VConstants.family,new Say("idatakemas"),new WaitFamily(20),new Move((HashMapData) house.get("dining room"),"movediningroom"));
		//move to dining room
		//if dining room is empty then move to storage and bring food out
		//look at all food.  Grab food with the least amount of age left 
		//wait for everyone else
		//first just grab the food and bring back any extra  should wait
		//say "idatakemas"
		
		
//		person.getTemplate().pending.add(new Move((HashMapData) house.get("dining room"),"movediningroom"));
//		person.getTemplate().pending.add(new MoveClosest(VConstants.gate,"chair"));
		//person.getTemplate().pending.add(new Consume(null,(HashMapData) house.get("dining room")));
		
		return new Returnable(false, 0);
	}

	@Override
	public OObject clone() {
		
		return new Eat();
	}
	
}
