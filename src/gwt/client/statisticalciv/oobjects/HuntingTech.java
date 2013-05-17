package gwt.client.statisticalciv.oobjects;

import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;

public class HuntingTech extends OObject {

	/**
	 * To start with.  
	 * 
	 * Occasionally create an animal
	 * 
	 * occasionally call hunt animal
	 */
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if(VConstants.getRandom().nextDouble() > .95){
			LivingBeing sheep=RandomPersonCreation.createPerson("sheep");
			person.getParent().getParent().add(Direction.getRandom(), sheep);
		}
		
		if(VConstants.getRandom().nextDouble() > .95){
			addChild(new HuntAnimal());
		}
		return null;
	}

	@Override
	public OObject clone() {
		return new HuntingTech().copyProperties(this);
	}
}
