package gwt.client.statisticalciv.rules;

import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class Disaster implements PBaseRule{

	

	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		// TODO Auto-generated method stub
		//if a certain demographic % is above x then run
		//a formula that takes the % over and increases the chance
		//of pulling a story linerally
		//all stories are  added to demographics for now
		//demographics then adds them to the game on the first run
		//which the pbase rules run from	

		return true;
	}

}
