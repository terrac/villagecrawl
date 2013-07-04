package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.generator.setup.DiseaseSetup;
import gwt.client.statisticalciv.generator.setup.FishingSetup;
import gwt.client.statisticalciv.generator.setup.PromoteTechs;
import gwt.client.statisticalciv.generator.setup.TradingSetup;
import gwt.shared.datamodel.VParams;

public class TechnologyRule extends VParams {

	public TechnologyRule() {
		// TODO Auto-generated constructor stub
	}
	
	
	int countBeforeNewEra = 5000;//5000
	int count;
	int level = 0;
	VillageRule vr;
	@Override
	public void execute(Map<String, Object> map) {
		count++;
		if(count > countBeforeNewEra){
			if(level < 1){
				level++;
			}
			if(level == 1&& vr == null){
				vr = new VillageRule();
			}
			count =0;
		}
		if(vr != null){
			vr.execute(map);
		}
		
		//pull variables off of the tech

	}
	
	
	
	@Override
	public PBase clone() {
		return new TechnologyRule().copyProperties(this);
	}
	public static PBase getTech(LivingBeing person) {
		return person.getType(VConstants.population).getType(VConstants.technology);
	}

	
	


	public static int getDefaultInt(String people, int i) {
		PBase pb= EntryPoint.game.getPBase(VConstants.technology).getPBase(VConstants.map).getPBase(people);
		if(pb == null){
			return i;
		}
		return PBase.getDefaultInt(pb, VConstants.size, i);
				
	}
	
	public static int getDefaultInt(String type,String name,String key, int i) {
		PBase pb= EntryPoint.game.getPBase(VConstants.technology).getPBase(type).getPBase(name);
		if(pb == null){
			return i;
		}
		return PBase.getDefaultInt(pb, key, i);
				
	}



	public static double getDefaultDouble(LivingBeing person,String name, double d) {
		return PBase.getDefaultDouble(person.getPopulation().getType(VConstants.technology), name, d);
	}



	public static double getDefaultDouble(String name, double d) {
		return PBase.getDouble(EntryPoint.game.getTechnology().getType(VConstants.main), name);
	}


	


}
