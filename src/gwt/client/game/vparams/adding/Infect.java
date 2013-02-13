package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

import java.util.Map;
import java.util.Map.Entry;

public class Infect {
	public Infect() {
		
	}


	
	
	public static void infect(LivingBeing lb,String stat){
		if(VConstants.getRandom().nextDouble() > .75){
			return;
		}
		HashMapData hmd=lb.getParent().getParent().getNearKeyValue(VConstants.livingbeing, null, lb, 1);
		if(hmd != null){
			PBase.increment(lb.getType(VConstants.temporary).getType(VConstants.stats),stat,5);
			
		}
	}
	

}
