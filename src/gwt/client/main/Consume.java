package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.TradeRoute;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;

public class Consume extends OObject {

	public Consume() {
	}
	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
		//check body and house for water and food
//		System.out.println("consume");
		if(person.getStats().getInt(VConstants.hunger)- EntryPoint.game.getTurn()>0){
			return new Returnable(false, 1, true);
		}
		person.getStats().put(VConstants.hunger, EntryPoint.game.getTurn()+ 300);
		if(person.getItems() != null){
			for(Item item:person.getItems().values()){
				if(item.getB(VConstants.food)){
					person.getStats().put(VConstants.hunger, 0);
					//person.getEconomy().lowerPriority(person);
					return new Returnable(false, 1, true);
				}
			}
		}
		
		
	
		return new Returnable(false,1);
	}

	@Override
	public OObject clone() {
		
		return new Consume().copyProperties(this);
	}
}
