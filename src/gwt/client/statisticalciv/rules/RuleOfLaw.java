package gwt.client.statisticalciv.rules;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;

public class RuleOfLaw {

	public static void checkFailure(HashMapData city) {
		Demographics demo = DemographicRule.getDemo(city);
		//turn into a point based formula
		//add check for above max size
		boolean tooLarge = VConstants.getRandom().nextDouble() > .7&& 
				demo.getMaxVillages() < DemographicRule.getSingleton().getVillageCount(demo);
		if(demo.getSize() < demo.getMaxVillageSize()/2||tooLarge){
			String leader = demo.getLeader();
			PBase newLeader = DemographicRule.getSingleton().getNextLeader();
			for(HashMapData hmd: DemographicRule.getSingleton().villageList){
				Demographics demo2 = DemographicRule.getDemo(hmd);
				if(leader.equals(demo2.getLeader())&&VConstants.getRandom().nextBoolean()){
					Demographics.getCulture(demo2).remove(leader);
					DemographicRule.getSingleton().addLeader(newLeader, hmd);
					{
						DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
								 new UImage("/images/"+VConstants.damage+"fireball.png")));
						displayPopup.displaypopup(hmd,hmd.getPosition(),  3);							
					}
					hmd.getMapData(VConstants.gate).put(VConstants.overlay,newLeader.getS(VConstants.overlay));
					
				}
			}
		}
		
	}

}
