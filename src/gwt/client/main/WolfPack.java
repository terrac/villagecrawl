package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class WolfPack extends OObject {
	int count;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
//		if(deer is on map){
//			run towards deer including crossing map lines
//			once one deer is eaten (the closest) 
//			then wait for a while and head off in direction of deer
//		}
		
		//count is less than deer count
		//eventually it could be stamina
		if(count == 10){
			person.getGroup().askAll(new MoveRandomFullMapData(1,"findingwheatfield"),person,person.getGroup().getFamily());
		}
		
		return null;
	}

}
