package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class BodyStats extends VParams {

	

	public BodyStats() {

	}

	
	String[] needs = new String[]{VConstants.hunger,VConstants.thirst
			//,VConstants.lavatory,VConstants.play
			};
	@Override
	public void execute(Map<String, Object> map) {
//		LivingBeing lb=getLivingBeing(map);
//		// add 1 to each need stat
//		for(String n : needs){
//			lb.getStats().increment(n);
//			if(lb.getStats().getInt(n) > 90){
//				lb.getEconomy().addNeed(n,2);
//			}
//		}
		//if stat reaches 30 then add the need
		
		//check and remove the need from the personal economy of the current person
		//maybe clear template as well
	
		
		
	}



	@Override
	public PBase clone() {
		return new BodyStats().copyDeepProperties(this);
	}

}
