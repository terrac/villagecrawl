package gwt.client.statisticalciv;

import java.util.HashMap;
import java.util.Map;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.main.base.PBase;
import gwt.client.statisticalciv.rules.Age;
import gwt.client.statisticalciv.rules.DemographicTimeRule;

public class Statistics extends PBase{
	static String violence = "violence";
	static String death = "death";
	static String human = "human";
	static String natural = "natural";
	static String age = "age";
	static String born = "born";
	static String[] natural_violent_deaths={natural,violence,death};
	public static String[] human_violent_deaths={human,violence,death};
	static String[] age_deaths={age,death};
	static String[] bornA={born};
	static Statistics statistics = new Statistics();
	public static Statistics getSingleton(){
		return statistics;
	}
	static int total = 0;
	public void addKills(double amount, boolean natural){
		s.addPoint(DemographicTimeRule.year, amount);
		total += amount;
		sTotal.addPoint(DemographicTimeRule.year, total);
        if(natural){
        	sNaturalDeaths.addPoint(DemographicTimeRule.year, amount);
        } else {
        	sHumanDeaths.addPoint(DemographicTimeRule.year, amount);
    		
        }
	}
	public void addAverage(double d) {
		sAverageAge.addPoint(DemographicTimeRule.year, d);
	}
	public String getKey(String...strings){
		return strings.toString();
	}
	public String getLast(String...strings){
		return strings[strings.length-1];
	}
	Map<Integer,PBase> intervalList = new HashMap();
	public void timeInterval(int interval){
		
		if(interval % 10 == 0){
			Age.addStatistics();
		}
		Age.resetStats();
	}
	HorizontalPanel hp = new HorizontalPanel();
	Chart c = new Chart();
	Series s=c.createSeries();
	Series sTotal = c.createSeries();
	Series sAverageAge = c.createSeries();
	Series sNaturalDeaths = c.createSeries();
	Series sHumanDeaths = c.createSeries();
	{
		s.setName("Deaths");
		sTotal.setName("Total Violent Deaths");
		sAverageAge.setName("Average Age");
		sNaturalDeaths.setName("Natural Deaths");
		sHumanDeaths.setName("Human Deaths");
		c.addSeries(s);
		c.addSeries(sTotal);
		c.addSeries(sAverageAge);
		c.addSeries(sNaturalDeaths);
		c.addSeries(sHumanDeaths);
	}
	
	public Widget getWidget() {
		return hp;
	}
	public BeforeSelectionHandler<Integer> getSelectionHandler() {
		return new BeforeSelectionHandler<Integer>() {
			
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {

				
				c.setTitle("deaths");
				
		        c.setType(Series.Type.LINE)  ;
				c.redraw();
				
				hp.add(c);
				intervalList.clear();
			}
		};
	}
	
}
