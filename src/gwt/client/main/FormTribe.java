package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;

public class FormTribe extends OObject {



	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		MapData md=person.getGroup().getTribe();
		
		if(md == null){
			person.getGroup().getParent().put(VConstants.tribe,new MList<Person>(fullMapData.getPeople()));
		}
		return new Returnable();
	}

}
