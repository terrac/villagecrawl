package gwt.client.statisticalciv.rules;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class CauseDeaths implements PBaseRule{
	double amt;
	public CauseDeaths() {
	
	}
	public CauseDeaths(double amt) {
		this.amt = amt;
	}
	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		Age.kill(p, Age.YOUNG_ADULT, amt *p.getDouble(VConstants.size));

		return true;
	}
}
