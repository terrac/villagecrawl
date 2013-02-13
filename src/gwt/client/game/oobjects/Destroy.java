package gwt.client.game.oobjects;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.display.LogDisplay;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;

public class Destroy extends OObject {
	public Destroy() {	
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		person.death();
		return null;
	}
	
	@Override
	public OObject clone() {
		
		return new Destroy().copyProperties(this);
	}
}
