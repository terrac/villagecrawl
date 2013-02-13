package gwt.client.game.oobjects;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class Guard extends OObject {

	

		
	
		public Guard() {
	
		}
		@Override
		public Returnable execute(FullMapData fullMapData, LivingBeing person) {
			// get nearest guard marker
			// move to it
			
			//if no guard markers
			//move to house
			// get nearest person
			// call trade protection/money (the protection can only be sold in a way that it is sold say once a week so that guards don't repeatedly ask the same
			//people for money
			return null;
		}
	
		@Override
		public OObject clone() {
			return new Guard().copyProperties(this);
		}
	
	


}
