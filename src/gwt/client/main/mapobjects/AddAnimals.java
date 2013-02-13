package gwt.client.main.mapobjects;

import java.util.List;

import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MapObject;
import gwt.client.main.base.OObject;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.SymbolicMap;

public class AddAnimals implements MapObject {

	@Override
	public Returnable execute(FullMapData fmd) {

		
		
			if(VConstants.getRandom().nextInt(20) == 0){

				addPack(fmd, "wolf");
					
			}
			if(VConstants.getRandom().nextInt(20) == 1){

				addPack( fmd, "deer");
				System.out.println(fmd.getPosition());	
			}
			
		
		
		return null;
	}

	public static void addPack( FullMapData fmd, String type) {
		List<LivingBeing> family = null;
		for(int i = 0; i < 5 ; i++){
			LivingBeing lb = fmd.getParent().getParent().addAnimal(type);
			fmd.add(Direction.West, lb);
			
			if(i == 0){
				//set leader variable
				lb.setVariable(VConstants.leader, lb);
				family = lb.getGroup().getFamily();
			}
			family.add(lb);
			lb.getGroup().setFamily(family);
			//get one family list, set for all families
			//add onto that list
			
		}
	}

}
