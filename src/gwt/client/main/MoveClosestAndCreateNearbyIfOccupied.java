package gwt.client.main;

import gwt.client.item.SimpleMD;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class MoveClosestAndCreateNearbyIfOccupied extends OObjectB {
	public MoveClosestAndCreateNearbyIfOccupied() {
		// TODO Auto-generated constructor stub
	}
	String key;
	String type ;
	public MoveClosestAndCreateNearbyIfOccupied(String key, String type,
			int radius) {
		super();
		this.key = key;
		this.type = type;
		this.radius = radius;
	}


	int radius = 40;
	
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		
		addToList(person, new MoveClosest(key,"kiln",radius));
		addToList(person, new OObjectB() {
			
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				MapData md=person.getParent().getMapData(key);
				
				if(md == null||!type.equals(md.getValue() )){
					//check to make sure there are no unoccupied kilns within a 15 radius
					HashMapData hmd=fullMapData.getNearKeyValueUnoccupied(key, type, person, 15);
					if(hmd == null){
						//hmd=fullMapData.getNearestEmptyAndUnbuilt(person,10);
						hmd.put(new SimpleMD(key, type));
					}
					addToList(person, new Move(hmd, "movetonextopen"+type));
					
				}
				return null;
			}
		});
		
		
		return null;
	}

}
