package gwt.client.statisticalciv.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java_cup.shift_action;
import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.datamodel.VParams;

public class DemographicRandomEffects extends VParams {

	public DemographicRandomEffects() {
		// TODO Auto-generated constructor stub
		nextRandom();
		nextRandom();
		nextRandom();
		nextRandom();
		nextRandom();
	}


	@Override
	public PBase clone() {

		return new DemographicRandomEffects();
	}

	public static void addTrade() {
		final BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);

//		MapDataAreaMap bm = bagMap.getBagMap();
		final SimpleMD data = new SimpleMD(VConstants.visualdamage, "green-effect");
		AttachUtil.attach(AttachUtil.placed, new VParams() {
			@Override
			public void execute(Map<String, Object> map) {
				HashMapData hashmapdata = (HashMapData) map
						.get(AttachUtil.OBJECT);
				hashmapdata = hashmapdata.getParent().getNearKeyValue(VConstants.gate, SConstants.farm, hashmapdata, 3);
				if(hashmapdata == null){
					return;
				}
				CultureTrade.doCultureTrader(hashmapdata);
				endSelection(bagMap);

			}

			
		}, data);

		shiftDown(data);

	}
	
	public static void addStory(final BasicStory bs) {
		final BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);

//		MapDataAreaMap bm = bagMap.getBagMap();
		final SimpleMD data = new SimpleMD(VConstants.visualdamage, bs.popup);
		AttachUtil.attach(AttachUtil.placed, new VParams() {
			@Override
			public void execute(Map<String, Object> map) {
				if(bagMap.selected == null){
					return;
				}
				HashMapData hashmapdata = (HashMapData) map
						.get(AttachUtil.OBJECT);
				hashmapdata = hashmapdata.getParent().getNearKeyValue(VConstants.gate, SConstants.farm, hashmapdata, 3);
				if(hashmapdata == null){
					return;
				}
				bs.run(DemographicRule.getDemo(hashmapdata),hashmapdata,null);
				endSelection(bagMap);

			}

			
		}, data);

		shiftDown(data);

	}

	public static void nextRandom() {
//		PBase next = VConstants.getRandomFromPBase3(EntryPoint.getCulture(VConstants.randomeffect).getList(VConstants.list));
//		if(next == null){
//			return;
//		}
		if(DemographicTimeRule.year > 50){
			return;
		}
		addStory(VConstants.getRandomFromList(bsList));
	}

	static List<BasicStory> bsList = new ArrayList<BasicStory>();static{
		bsList.add(DemographicRule.fertility);
		bsList.add(DemographicRule.fight);
		bsList.add(DemographicRule.flood);
		bsList.add(DemographicRule.settle);
	}
	// fills in any missing spaces
	public static void shiftDown(MapData data) {

		BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);
		MapDataAreaMap bm = bagMap.getBagMap();

		for (int a = bm.getYsize(); a >= 0 ; a--) {
			if (bm.getData(0, a) == null) {
				continue;
			}
			bm.setData(0, a + 1, bm.getData(0, a));
		}
		bm.setData(0, 0, data);

		// call next
		bagMap.update();
		bagMap.update();
	}
	public static void endSelection(final BagMap bagMap) {

//		bagMap.getBagMap().setData(bagMap.selected, null);
//		MapData data = bagMap.getBagMap().getData(new Point(bagMap.getBagMap().getXsize()-1,bagMap.getBagMap().getYsize()-1));
//		bagMap.setSelection(data);
//		bagMap.selected = null;
		bagMap.update();
		
		nextRandom();
	}
	
	public static void removeSelection(){
		BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);
			//bagMap.getBagMap().setData(bagMap.selected, null);
			bagMap.selected = null;			
			bagMap.update();
			
	}
}
