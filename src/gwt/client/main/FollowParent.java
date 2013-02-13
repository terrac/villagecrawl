package gwt.client.main;

import java.util.Arrays;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;

public class FollowParent extends OObject {
	
	//for when parent is moving
	


	public FollowParent() {
	
	}
	
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		LivingBeing mom = null;
		for(LivingBeing lb :person.getGroup().getFamily()){
			if("femaleadult".equals(lb.getType())){
				mom = lb;
			}
		}
		addToList(person, new Move(mom.getParent(), "followparent"));
		return null;
	}
	
	@Override
	public OObject clone() {
		
		return new FollowParent();
	}


	public static boolean isWithin(LivingBeing mom, LivingBeing person) {
		if(Point.distance(mom.getX(), mom.getY(),person.getX(),person.getY())>7){
			return false;
		}
		return true;
	}
}
