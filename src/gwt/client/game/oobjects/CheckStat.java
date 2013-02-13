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
import gwt.client.map.runners.GetForNearby;

public class CheckStat extends OObject  {
	

	public CheckStat() {

	}
	public CheckStat(String string) {
		put(VConstants.name,string);
	}
	@Override
	public Returnable execute(FullMapData fullMapData, final LivingBeing person) {
		String name = getS(VConstants.name);
		Integer in=(Integer) person.getStats().get(name);
		int turn = EntryPoint.game.getTurn();
		if(in == null){
			
			person.getStats().put(name, turn+600);
		} else {
//			if(turns are less than the turn held in the corresponding stat location){
//			//break because we don't want to continue
			if(in < turn){
				return new Returnable(false, 0, true);
			}
		}
		//LogDisplay.log(person.getType()+" starting to "+name, 2);
		
		return null;
	}
	@Override
	public OObject clone() {
		
		return new CheckStat().copyProperties(this);
	}
}
