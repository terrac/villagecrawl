package gwt.client.main;

import java.util.Arrays;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;

public class ChildHuntAndGather extends OObject {
	
	public ChildHuntAndGather() {
	
	}
	
	
	public ChildHuntAndGather(String...strings) {
		put(VConstants.type,strings);
	}
	
	
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		addToList(person, new MoveRandomHashMapData("huntandgather"));
		HMapData hMapData = (HMapData) person.getParent().get(VConstants.temporary);
		if(hMapData == null){
			return new Returnable(true, 1);
		}
		
	
		for(MapData item:hMapData.removableValues()){
			String type1 = (String) item.get(VConstants.type);
			if(type1 == null){
				continue;
			}
			if(!getListCreate(VConstants.type).contains(type1)){
				continue;
			}
			person.getItems().put(item);
		}
		
		
		return new Returnable(true, 1);
	}
	@Override
	public OObject clone() {
		
		return new ChildHuntAndGather().copyProperties(this);
	}

}
