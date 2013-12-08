package gwt.client.game.display;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.statisticalciv.rules.DemographicRule;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

import java.util.Map;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series.Type;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DemographicDisplay extends HMDDisplay {
	public DemographicDisplay() {
	
	}

	@Override
	public void execute(Map<String, Object> map) {
		setup(map);
		if(current == null){
			return;
		}

		super.execute(map);
		MapData md=current.getMapData(VConstants.gate);
		if(Demographics.chart&&md != null&& md.containsKey(VConstants.demographics)){
			
			DemographicRule.Demographics demo = (Demographics) md.get(VConstants.demographics);
			demo.execute();					
			
			
		} else {

			super.execute(map);
		}
	}

	@Override
	public void init() {
		if(!Demographics.chart){
			super.init();
			return;
		}
		// TODO Auto-generated method stub
		super.init();
		dhmd.clear();
		dhmd.add(DemographicRule.Demographics.c);
	}
}
