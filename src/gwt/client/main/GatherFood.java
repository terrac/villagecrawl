package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;

public class GatherFood extends OObjectB {

	String[] list = new String[]{"wild onions","mushrooms","grass","carrots","oats","barley","sorgum"};

	public GatherFood(String ... l) {
		list = l;
	}
	public GatherFood() {
		
	}
	@Override
	public Returnable execute(final FullMapData fullMapData, LivingBeing person) {
		for(final String value : list){
			HashMapData hmd=fullMapData.getNearby(person, new GetForNearby<HashMapData>() {

				@Override
				public HashMapData get(int x1, int y1) {
					HashMapData hmd = null;
					
					MapData md=fullMapData.getData(x1, y1).getMapData(VConstants.foodgroup);
					
					if(md != null&&value.equals(md.getValue())){
						hmd= fullMapData.getData(x1, y1);
						
					
					}
					if(md != null){
						if(((Plant)md).getHealth() < 80){
							return null;
						}
					}
					return hmd;
				}
			}, 10);
			if(hmd == null){
				continue;
			}
			addToList(person, new Move(hmd, "gather"));
			addToList(person, new Gathering());
		}
		
		return null;
	}

	
}
