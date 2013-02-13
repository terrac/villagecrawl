package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;

public class DestroyPlantAndSeed extends OObject {
	
	public DestroyPlantAndSeed() {
		// TODO Auto-generated constructor stub
	}
	String type;
	public DestroyPlantAndSeed(String type) {
		this.type = type;
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		if(null ==person.getParent().remove(VConstants.foodgroup)){
			return null;
		}
		Plant md = new Plant(type,5);
		
		person.getParent().put(md);
		return new Returnable();
	}

}
