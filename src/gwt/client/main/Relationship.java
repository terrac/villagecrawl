package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class Relationship extends OObjectB {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		LivingBeing p=person.getGroup().getRelationship();
		if( p == null){
			if(person.getGroup().getTribe() == null){
				// should go outside of tribe
				return null;
			}
			for(LivingBeing pT :person.getGroup().getTribe()){
				//if(pT.getGroup().getRelationship() == null&&!person.getStats().getSex().equals(pT.getStats().getSex())){
					person.getGroup().put(VConstants.relationship,pT);
					p = pT;
					continue;
				//}
			}
		}
		addToList(person, new MakeBaby());
		//states to form relationship
		//state 1 physical attraction (search based on tribe)
		//state 2 compatibility (dunno, stubbed)
		//state 3 date , suggest a date
		
		//state 4 relationship formed
		
		//add on template for a relationship
		
		//make baby
		// raise child
		//repeat
		return null;
	}

}
