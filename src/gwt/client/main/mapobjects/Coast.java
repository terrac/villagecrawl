package gwt.client.main.mapobjects;

import java.util.List;

import gwt.client.main.MapArea;
import gwt.client.main.Person;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MapObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.under.Fish;
import gwt.client.main.base.under.Water;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.SymbolicMap;
import gwt.client.personality.Stats;

public class Coast implements MapObject {
	public Coast() {
	}
	@Override
	public Returnable execute(FullMapData fmd) {

					
		if(fmd.getX() == fmd.getParent().getXsize() ){
			for(int x = fmd.getXsize() /2; x < fmd.getXsize() ; x++){
				for(int y = 0; y < fmd.getYsize(); y++){
					//fmd.getData(x, y).put(new Water());
					fmd.getData(x, y).put(new Fish());
				}
			}
		}
				
			
			
		
		
		return null;
	}

}
