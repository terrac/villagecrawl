package gwt.client.statisticalciv.rules;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.Statistics;
import gwt.client.statisticalciv.UVLabel;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class DemographicTechRule extends VParams {

	public static void techRule(){
		//run every 10 turns
		
		//loop through villages, get demographic
		//loop through techs and recompute values
	}
	
	public static double getBirthRate(Demographics d){
		return 0;
	}
	public static double getCombatRate(Demographics d){
		return 0;
	}
	
	
	public static double getTechScore(Demographics demo,String key) {
		return demo.getType(Demographics.technologyMap).getType(key).getDouble(VConstants.percent);
	}
	public void addTech(String s,Demographics demo,double percent) {
		if(s == null){
			return;
		}
		//PBase.addToListIfNotExists(getDemo(hmd),VConstants.technology,s);
		//PBase.addToListIfNotExists(getDemo(hmd),Demographics.technologyColor,getTech(s).getS(VConstants.color));
		demo.getType(Demographics.technologyMap).put(s, new PBase(VConstants.percent,percent));
		
	}
	
	static DemographicTechRule singleton;
	static {
		singleton = new DemographicTechRule();
	}
	public static DemographicTechRule getSingleton() {
		return singleton;
	}
	@Override
	public PBase clone() {
		return new DemographicTechRule().copyProperties(this);
	}
	
	
}
