package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class Story extends OObjectB {

	String[] types = new String[]{"scary","funny","moralitic"};
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if(person != person.getVariable(VConstants.doer)){
			//set animation tell story
		} else {
			//set animation listen
		}				

		return null;
	}

}
