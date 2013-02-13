package gwt.client.main.base;

import gwt.client.game.AttackEnemyMeta;
import gwt.client.item.Item;
import gwt.client.main.ChildHuntAndGather;
import gwt.client.main.ChildsPlay;
import gwt.client.main.FollowParent;
import gwt.client.main.HuntAndGather;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.PickUp;
import gwt.client.main.ReturnFood;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.client.person.Building;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class FollowParentMeta extends PBase implements MetaOObject,Serializable {

	public FollowParentMeta() {
	
	}
	@Override
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject current, Returnable ret) {
		
		
		
	}
	@Override
	public Returnable preExecute(final FullMapData fullMapData,final LivingBeing person,
			final OObject current) {
		
		//get mom
		LivingBeing mom = null;
		for(LivingBeing lb :person.getGroup().getFamily()){
			if("femaleadult".equals(lb.getType())){
				mom = lb;
			}
		}
		
		if(!FollowParent.isWithin(mom,person)){
			if(!(person.getTemplate().getCurrent().instanceOf(FollowParent.class))){
				person.getTemplate().clear();
				//OutputDirector.mpane.say(person.getName(),"Mommy!");
				
				OObject.addToList(person, new FollowParent());
				return null;
			} else {
				return new Returnable(true);
			}
			
		}
		OObject momcur = mom.getTemplate().getCurrent();
		if(momcur != null&&momcur.instanceOf(HuntAndGather.class) ){
			if(!(person.getTemplate().getCurrent().instanceOf(ChildHuntAndGather.class))){
				person.getTemplate().clear();
				ChildHuntAndGather childHuntAndGather = new ChildHuntAndGather();
				childHuntAndGather.put(VConstants.type,momcur.getTopOParent().get(VConstants.type));
				OObject.addToList(person, childHuntAndGather);
				return null;
			} else {
				return new Returnable(true);
			}
		}
		if(!(person.getTemplate().getCurrent().instanceOf(ChildsPlay.class))){
			person.getTemplate().clear();
			OObject.addToList(person, new ChildsPlay());
			//OutputDirector.mpane.say(person.getName(),"play");
			
			return null;
		} else {
			return new Returnable(true);
		}
		//do a mock action based on the mom
		//first make sure mom is within a certain distance.  If not then move to within that distance
		//then get the action that the mom is doing and execute a mapped object based on the higest oob parent (if current template is empty
		//map huntandgather to a childhuntandgather
		//map ooblist to play ooblist( chase after each other, mimic parent)
		
		
		
	}

	
	@Override
	public PBase clone() {
		
		return new FollowParentMeta().copyProperties(this);
	}
}
