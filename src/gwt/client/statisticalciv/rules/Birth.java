package gwt.client.statisticalciv.rules;

import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

public class Birth implements PBaseRule{
	double amt;
	
	public Birth() {
		// TODO Auto-generated constructor stub
	}
	public Birth(double amt) {
		this.amt = amt;
	}
	
	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		Age.birth((Demographics) p, amt);

		return true;
	}
}
