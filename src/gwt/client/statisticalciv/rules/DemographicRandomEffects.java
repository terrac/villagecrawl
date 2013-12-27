package gwt.client.statisticalciv.rules;

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
	
	public static void addDisaster() {
		final BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);

//		MapDataAreaMap bm = bagMap.getBagMap();
		final SimpleMD data = new SimpleMD(VConstants.visualdamage, "waterdamage");
		AttachUtil.attach(AttachUtil.placed, new VParams() {
			@Override
			public void execute(Map<String, Object> map) {
				HashMapData hashmapdata = (HashMapData) map
						.get(AttachUtil.OBJECT);
				hashmapdata = hashmapdata.getParent().getNearKeyValue(VConstants.gate, SConstants.farm, hashmapdata, 3);
				if(hashmapdata == null){
					return;
				}
				DemographicRule.flood.run(DemographicRule.getDemo(hashmapdata),hashmapdata,null);
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
		int count = VConstants.getRandom().nextInt(2);
		if(count == 0){
			//start cultural trade on click
			addTrade();
		}
		else if(count == 1){
			//red effect, shows water image, says massive flooding
			// (note, make sure regular disasters do the images
			addDisaster();
			
		}
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
		bagMap.getBagMap().setData(bagMap.selected, null);
		
		bagMap.selected = null;
		bagMap.update();
		
		nextRandom();
	}
}
