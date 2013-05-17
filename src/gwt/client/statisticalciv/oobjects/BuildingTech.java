package gwt.client.statisticalciv.oobjects;

import gwt.client.main.Returnable;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class BuildingTech extends OObject {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OObject clone() {
		return new BuildingTech().copyProperties(this);
	}
}
