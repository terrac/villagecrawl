package gwt.client.game.oobjects;

import gwt.client.game.VisualDamage;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class Healing extends OObject {
	public Healing() {
		put(VConstants.overlay,"heart");
	}
	
	LivingBeing sickee;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		getSickee(fullMapData);
		
		if(sickee == null){
			return null;
		}
		if(sickee.getStats().getInt(VConstants.sickness) <= 0&&sickee.getStats().isHealthy()){
			sickee = null;
			getSickee(fullMapData);
		}
		
		if(sickee == null){
			return null;
		}
		sickee.getParent().put(new VisualDamage(VConstants.damage+"heal"));
		if(Point.distance(sickee, person)> 2){
			addToList(person, new Move(sickee.getParent(), ""));				
		}
		int health=sickee.getStats().getHealth();
		health++;		
		sickee.getStats().put(VConstants.health, health);
		
		
		int sickness=sickee.getStats().getInt(VConstants.sickness);
		sickness--;		
		sickee.getStats().put(VConstants.sickness, sickness);
		
		return null;
	}


	public void getSickee(FullMapData fullMapData) {
		if(sickee == null){
			for(LivingBeing b : fullMapData.people){
				if(b.getStats().getInt(VConstants.sickness) > 0||b.getStats().isHealthy()){
					sickee = b;
					break;
				}
			}	
		}
	}
	

	@Override
	public OObject clone() {

		return new Healing().copyProperties(this);
	}
}
