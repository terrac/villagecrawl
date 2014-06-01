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

public class DemographicTimeRule extends VParams {

	public static boolean yearChange;
	public DemographicTimeRule() {
	}
	
	public DemographicTimeRule(Object... vp) {
		super(vp);
	}
	List<PBase> techs = new ArrayList();
	{
		techs.add(new PBase(VConstants.name,Demographics.slavery,VConstants.count,1));
		techs.add(new PBase(VConstants.name,Demographics.prostution,VConstants.count,2));
		techs.add(new PBase(VConstants.name,Demographics.earlyDisease,VConstants.count,3));
	}
	
	public static int year = 0;
	double yearPct = .00;
	double increment = .07;
	@Override
	public void execute(Map<String, Object> map) {
		yearPct+= increment;
		yearChange = yearPct > 1;
		if(yearChange){
			Statistics.getSingleton().timeInterval(year);
			yearPct = .00;
			year++;
			for(PBase pb : techs){
				if(pb.getInt(VConstants.count) < year){
					DemographicRule.getSingleton().enableTech(pb.getS(VConstants.name));
					techs.remove(pb);
					break;
				}
			}
		}
		
		UVLabel label=(UVLabel) EntryPoint.game.get(VConstants.score);
		label.setText(year+" "+NumberFormat.getDecimalFormat().format(yearPct));
		label.getWidget().setWidth("4em");
	}
	
	@Override
	public PBase clone() {
		return new DemographicTimeRule().copyProperties(this);
	}

}
