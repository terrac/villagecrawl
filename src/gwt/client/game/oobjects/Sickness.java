package gwt.client.game.oobjects;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.WaitMove;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class Sickness extends OObject {
	public Sickness() {
		put(VConstants.overlay,"sickness");
	}
	public Sickness(String value) {
		
		put(VConstants.value,value);
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		addToList(person, new WaitMove(VConstants.sickness, 3));
		
		
		Integer health = (Integer) person.getStats().getHealth();
		health--;
		if(health < 0){
			person.death();
		}
		person.getStats().put(VConstants.health, health);
		
		
		int sickness=person.getStats().getInt(VConstants.sickness);
		sickness--;		
		person.getStats().put(VConstants.sickness, sickness);
		
		if(sickness < 0){
			return null;
		}
		return new Returnable(true);
	}

	@Override
	public OObject clone() {

		return new Sickness().copyProperties(this);
	}
}
