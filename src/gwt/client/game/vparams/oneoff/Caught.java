package gwt.client.game.vparams.oneoff;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class Caught extends VParams {
	public Caught() {
	}
	
	public Caught(int chance) {
		put(VConstants.chance,chance);
	}
	
	public void execute(java.util.Map<String, Object> map) {
		int chance = getInt(VConstants.chance);
		if(VConstants.getRandom().nextInt(100) <= chance){
			getLivingBeing(map).death();
		}
		
	}

	@Override
	public PBase clone() {

		return new Caught().copyProperties(this);
	}

}
