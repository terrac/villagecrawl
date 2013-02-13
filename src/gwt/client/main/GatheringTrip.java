package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class GatheringTrip extends OObjectB {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		addToList(person, new MoveRandomFullMapData("gather"));
		addToList(person, new GatherFood());
		addToList(person, new ReturnFood());
		
		return null;
	}

}
