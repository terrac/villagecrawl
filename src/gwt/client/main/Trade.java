package gwt.client.main;

import java.util.ArrayList;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.person.Building;
import gwt.client.stats.BStatistics;
import gwt.client.stats.CountValue;


public class Trade extends OObjectB{
	 String type;
	public Trade() {
		
	}
	
	

	
	public Trade(String type) {
		super();
		this.type = type;
	}

	

	public Returnable execute(FullMapData map, final LivingBeing person) {
		
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		Needs needs =(Needs) person.getGroup().get(VConstants.needs);
		HashMapData hmd=map.getNearby(person, new GetForNearby<HashMapData>(map) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.getLivingBeing() != null&&!hashmapdata.getLivingBeing().getGroup().getFamily().equals(person.getGroup().getFamily())){
					return hashmapdata;
				}
				return null;
			}
		}, 40);
		LivingBeing person2=hmd.getLivingBeing();
		Needs needs2 =(Needs) person2.getGroup().get(VConstants.needs);
		for(MapData md :((HashMapData)house.get(VConstants.storage)).getItems().values()){
			Item item = (Item) md;
			int amount=needs.getAmountNotNeeded(item,5);
			
			if(amount > 1){
				int amount2=needs2.getAmountNeeded(item,5);
				
				int min=Math.min(amount, amount2);
				person.getItems().add(-min,item);
				person2.getItems().add(min,item);
				
			}
		}
		return null;
	}




	
	
	
	
	

}
