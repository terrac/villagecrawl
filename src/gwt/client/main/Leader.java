package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MetaOObject;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class Leader implements MetaOObject {

	//register
	@Override
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject current, Returnable ret) {
		
	}

	@Override
	public Returnable preExecute(FullMapData fullMapData, LivingBeing person,
			OObject current) {
				return null;
		
	}
}
