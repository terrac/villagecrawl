package gwt.client.game;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;

public class TargetSelf extends OObject  {
	public TargetSelf() {
		
	}
	
	public TargetSelf(PBase pb) {
		put(VConstants.ability,pb);
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {				
		//apply action to self
		ApplyDamage ad = (ApplyDamage) person.getMapArea().game
		.get(VConstants.applydamage);
		ad.execute(person, null, (PBase) get(VConstants.ability));
		//
		return null;
	}
	@Override
	public OObject clone() {
		
		return new TargetSelf().copyProperties(this);
	}

	
	
}
