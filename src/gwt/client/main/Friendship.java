package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MetaOObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.Participants;
import gwt.client.main.base.PossibleParticipants;
import gwt.client.map.FullMapData;

public class Friendship implements MetaOObject {

	
	public Friendship() {
	
	}
/**
 * 	friends -> form relationships with people that are often nearby and do actions
	        -> do pair activities such as painting and watching, pottery, hunting, gathering, planting
	        -> building 
 * 
 */
	@Override
	public Returnable preExecute(FullMapData fullMapData, LivingBeing person,OObject oobject) {
		
//		//other people also get social bonuses
//		Object variable = person.getVariable(VConstants.doer);
//		if(variable != null&&!person.equals(variable)){
//			person.getStats().socialmeter++;
//			return null;
//		}
//		
//		//person gets a social bonus while working
//		if(person.getVariable("friendship") != null){			
//			person.getStats().socialmeter++;
//			
//				return null;
//			 
//		}
//		// don't start another social task.
//		if(person.getStats().socialmeter > 200){
//			person.getStats().socialmeter--;
//			return null;
//		}
//		
//		
//		if(person.getGroup().getFriends().size() == 0){
//			for(LivingBeing p :person.getGroup().getTribe()){
//					if(p.equals(person)){
//						continue;
//					}
//					person.getGroup().getFriends().add(p);
//					p.getGroup().getFriends().add((Person) person);
//					
//				
//			}
//			
//		}
//		if(oobject.instanceOf(PossibleParticipants.class)){
//			Participants participants = ((PossibleParticipants)oobject).getPartcipants();
//		
//			person.setVariable("friendship", oobject);
//			person.getGroup().setDoer(oobject,person, person.getGroup().getFriends());
//			
//			return null;
//		}
//		if(person.getStats().socialmeter < 10){
//			OObject obj;
//			
//			MList<OObject> olist=(MList<OObject>) person.getGroup().get(VConstants.friendship);
//			obj = olist.getRandom();
//			
//			person.getTemplate().pending.add(obj);
//			callFriends(person,obj);
//			
//		}

		
		
		//if no friends then search for potential friend
		
		//state2
		//suggest activity
		
		
		//the oobject is possible activity 
		
		//pull friend activities from group based on type and statistics
		//ie two men might hunt, two women might do pottery, and two children might paint

		
		
		return null;
	}

protected void callFriends(LivingBeing person, OObject obj) {
	
	person.setVariable("friendship", obj);
	
	person.getGroup().askAll(obj,person, person.getGroup().getFriends());
}

@Override
public void postExecute(FullMapData fullMapData, LivingBeing person,
		OObject current, Returnable ret) {
	OObject oob=(OObject) person.getVariable("friendship");
	if(!ret.shouldcontinue&&current.equals(oob)){
		person.unsetVariable("friendship");
		
		
	}
	
}

}
