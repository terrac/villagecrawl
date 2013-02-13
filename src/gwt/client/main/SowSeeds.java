package gwt.client.main;

import java.util.ArrayList;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.person.Building;
import gwt.client.stats.BStatistics;
import gwt.client.stats.CountValue;


public class SowSeeds extends OObjectB{
	 String type;
	public SowSeeds() {
		
	}
	
	

	
	public SowSeeds(String type) {
		super();
		this.type = type;
	}

	

	public Returnable execute(FullMapData map, LivingBeing person) {
		
		Building wheatfield =(Building) person.getGroup().get("wheat field");

		//if no wheat field
		//find a valid fmd on the symbolic map by getting the nearest empty
		if(wheatfield == null){
			//FullMapData fmd=map.getParent().getNearestEmpty(map.getPosition().x, map.getPosition().y);
			//move to there, and build structure
			//bring along family to help
			//person.getGroup().askAll(new Move(md.getNearestEmpty(5, 5),"movetowheatfield"),person,person.getGroup().getFamily());
//			person.getGroup().askAll(new MoveRandomFullMapData(1,"findingwheatfield"),person,person.getGroup().getFamily());
			
			person.registerDecision(1,VConstants.farming,VConstants.family,new MoveRandomFullMapData(1,"findingwheatfield"),new SowSeedsPlant(type));
//			person.getGroup().askAll(new SowSeedsPlant(type),person,person.getGroup().getFamily());
		}else{
			person.getGroup().askAll(new Move(wheatfield.fmd,"movebacktowheatfield"),person,person.getGroup().getFamily());
			
			CountValue ifme = new CountValue(type);
			wheatfield.fmd.iterate(ifme);
			
			if(ifme.count > 6){
				person.getGroup().askAll(new SowSeedsPlant(type),person,person.getGroup().getFamily());
				
			} else {
				HashMapData hmd=map.getNearKeyValue(VConstants.foodgroup, VConstants.forest, person.getParent(), 40);
				if(hmd != null){
					//add need
					
					//this addition will also check the needs to see if it has been fufilled.
					//this should really probably be a seperate oobject as it seems like it would be a common occurrance
					addNeed(VConstants.cuttree);
					
				}
				person.getGroup().askAll(new GatherFood(type),person,person.getGroup().getFamily());
			}
		}
		
		return new Returnable(false, 0);
	}




	
	
	
	
	

}
