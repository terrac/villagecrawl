package gwt.client.game.oobjects;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.display.LogDisplay;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.getters.KeyValue;

public class MoveStore extends OObject {
	public MoveStore() {	
	}
	
	public MoveStore(String stored) {
		put(VConstants.name,stored);
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		String name = getS(VConstants.name);
		
		Object p = person.get(name);
		if(p instanceof String){
			OObject.addToList(person, new MoveClosest(VConstants.gate, (String) p));
			return null;
		}
		if(p == null){
			p = person.getPosition();
			person.put(name,p);
			return null;
		}
		//LogDisplay.log(p, 2);
		OObject.addToList(person, new Move(fullMapData.getData((Point) p),"moving"));
	
		return null;
	}
	
	@Override
	public OObject clone() {
		
		return new MoveStore().copyProperties(this);
	}
}
