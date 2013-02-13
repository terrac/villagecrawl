package gwt.client.game.oobjects;

import gwt.client.EntryPoint;
import gwt.client.game.Attack;
import gwt.client.main.Kill;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;

/**
 * 
 * @author terra
 * 
 */
public class HuntAnimal extends OObject {

	public HuntAnimal() {

	}

	/**
	 * Gets the closest non humanoid (where humanoid == null I think Attacks
	 * (animals have low stats and just run away mostly)
	 */
	@Override
	public Returnable execute(final FullMapData fullMapData, LivingBeing person) {
		LivingBeing an = null;
		for(LivingBeing lb :fullMapData.people){
			if(!lb.getB(VConstants.humanoid)){
				an = lb;
			}
		}
		
		if (an != null) {
			addToList(person, new Attack(an, person));
			addToList(person, new OObject() {
				
				@Override
				public Returnable execute(FullMapData fullMapData, LivingBeing person) {
					//person.getItemsCreate().put(EntryPoint.game.getItemMap().get("meat").clone());
					
					//add to gdp
					
					return null;
				}
			});
		}

		return null;
	}

	@Override
	public OObject clone() {

		return new HuntAnimal().copyProperties(this);
	}
}
