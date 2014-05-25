package gwt.client.statisticalciv.rules;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class CauseDeaths implements PBaseRule{
	double amt;
	double minAmount;
	public CauseDeaths() {
	
	}
	public CauseDeaths(double amt,double amt2) {
		this.amt = amt;
		minAmount = amt2;
	}
	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		double size = amt *p.getDouble(VConstants.size);
		size = Math.max(size, minAmount);
		Age.kill(p, Age.YOUNG_ADULT, size);
		RuleOfLaw.checkFailure(hmd);
		if(DemographicRule.getDemo(hmd).getSize() < 20 ){
			DemographicRule.removeVillage(hmd);
		}
		return true;
	}
}
