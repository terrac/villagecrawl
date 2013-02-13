package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gchart.client.GChart;
import com.googlecode.gchart.client.GChart.SymbolType;

public class PersonDisplay extends UIVParams {

	public PersonDisplay() {

	}

	public PersonDisplay(LivingBeing lb) {
		put(VConstants.livingbeing, lb);
	}

	@Override
	public void execute(final Map<String, Object> map) {
		LivingBeing lb = (LivingBeing) get(VConstants.livingbeing);
		if(lb == null){
			return;
		}
		GChart gc = new GChart(200, 150);
		
		vp.add(new Image(lb.getBigImage()));
		gc.setChartTitle("Person's stats");
		int count = 0;
		for (Entry<String, Object> en : lb.getStats().getObjMap().entrySet()) {
			if(en.getKey().contains("health")){
				continue;
			}
			Object o= en.getValue();
			if(o instanceof Integer){
				int value = (Integer) o;
				
				gc.addCurve();
				gc.getCurve().addPoint(count, value);
				gc.getCurve().getSymbol().setSymbolType(SymbolType.VBAR_SOUTH);
				gc.getCurve().getSymbol().setBackgroundColor(colors[count]);
				gc.getCurve().getSymbol().setBorderColor("black");
				gc.getCurve().getSymbol().setModelWidth(1.0);
				gc.getCurve().setLegendLabel(en.getKey());
				
				count++;
			}
		}
		gc.getXAxis().clearTicks();
		
		gc.update();
		vp.add(gc);
	}

	VerticalPanel vp = new VerticalPanel();

	@Override
	public UIVParams clone() {

		return new PersonDisplay().copyProperties(this);
	}

	@Override
	public Widget getWidget() {
		return vp;
	}
	final String[] colors =
	       {"red", "green", "yellow", "fuchsia", "silver", "aqua","blue","white","orange","purple"};
}
