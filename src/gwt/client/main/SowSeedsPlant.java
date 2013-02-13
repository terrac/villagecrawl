package gwt.client.main;

import java.util.ArrayList;
import java.util.List;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.person.Building;
import gwt.client.stats.BStatistics;


public class SowSeedsPlant extends OObjectB{
	 String type;
	public SowSeedsPlant() {
		
	}
	


	
	public SowSeedsPlant(String type) {
		super();
		this.type = type;
	}


//	BStatistics bs = new BStatistics();
//	{
//		bs.foodlist = new ArrayList<String>();
//		bs.foodlist.add(type);
//	}

	int count = 6;
	public Returnable execute(FullMapData map, LivingBeing person) {
		Building wheatfield =(Building) person.getGroup().get("wheat field");

		//if no wheat field
		//find a valid fmd on the symbolic map by getting the nearest empty
		if(wheatfield == null){
			person.getGroup().put(new Building("wheat field",map));
			addNeed(VConstants.cuttree);
		}
		//get nearest grass
		
		
		HashMapData hmd=person.getParent().getParent().getNearKeyValueUnoccupied(VConstants.foodgroup,"grass", person, 30);
		person.getTemplate().pending.add( new Move(hmd, "tograss"));
		person.getTemplate().pending.add( new DestroyPlantAndSeed(type));
		count--;
		if(count > 0){
			return new Returnable(true,0);
		}

		
		count = 6;
		
		return new Returnable(false, 1);
	}
	
	
	
	@Override
	public SowSeedsPlant clone() {
		
		return new SowSeedsPlant(type);
	}
	

}
