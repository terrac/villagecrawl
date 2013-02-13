package gwt.client.main;

import java.util.Arrays;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;

public class HuntAndGather extends ChildHuntAndGather {
	
	public static final String GATHERPOINT = "gatherpoint";
	public HuntAndGather() {
	
	}
	
	
	public HuntAndGather(String...strings) {
		super(strings);
	}
	
	@Override
	public void init(LivingBeing person) {
		//addToList(person, new MoveRandomFullMapData("huntandgather"));
		//addToList(person, new MoveClosest(GATHERPOINT,GATHERPOINT));
		addToList(person, new MoveRandomFullMapData("huntandgather"));
	}
	int count = 0;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		//randomly move around
		
		if(count > 10){
			OObject.addToList(person, new ReturnFood());
			
			return null;
		}
		super.execute(fullMapData, person);
		
		
		//given a list of things to hunt or gather, gather them
		
		//keep an optional additional object to determine fitness.  IE it might injure the person or give them extra
		
		count++;
		return new Returnable(true, 1);
	}
	@Override
	public OObject clone() {
		
		return new HuntAndGather().copyProperties(this);
	}

	
}
