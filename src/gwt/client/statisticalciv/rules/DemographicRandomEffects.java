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
import gwt.shared.datamodel.VParams;

public class DemographicRandomEffects extends VParams {

	public DemographicRandomEffects() {
		// TODO Auto-generated constructor stub
	}

	public void execute(java.util.Map<String, Object> map) {
		nextRandom();
		nextRandom();
		nextRandom();
		nextRandom();
		addBuilding("barn", "farms", null, null,10);
	}

	@Override
	public PBase clone() {

		return new DemographicRandomEffects();
	}

	public static void addHaggle(final int money) {
		final BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);

		MapDataAreaMap bm = bagMap.getBagMap();
		final SimpleMD data = new SimpleMD(VConstants.gate, "green-effect");
		AttachUtil.attach(AttachUtil.runbefore, new VParams() {
			@Override
			public void execute(Map<String, Object> map) {

				ItemsDisplay.getPerson("Gilgamesh").getItemsCreate().subtractMoneyAndThenItems(money);
				map.put(VConstants.haggle, true);
				((TradeCultureRoute) EntryPoint.game.getVParams().get("tcr")).doTrade("haggle");
				bagMap.getBagMap().setData(bagMap.selected, null);
				
				bagMap.selected = null;
				bagMap.update();
				
				nextRandom();

			}
		}, data);

		shiftDown(data);

	}

	public static void nextRandom() {
		PBase next = VConstants.getRandomFromPBase3(EntryPoint.getCulture(VConstants.randomeffect).getList(VConstants.list));
		if(next == null){
			return;
		}
		if(next.getS(VConstants.name).equals("building")){
			addBuilding(next.getS(VConstants.type), next.getS(VConstants.action), null, null,next.getInt(VConstants.money));
		}
		else if(next.getS(VConstants.name).equals(VConstants.haggle)){
			addHaggle(next.getInt(VConstants.money));
		}
	}

	public static void addBuilding(final String type, final String action,
			final String name, FullMapData fmd,final int money) {
		final BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);

		final SimpleMD data = new SimpleMD(VConstants.gate, type);
		
		AttachUtil.attach(AttachUtil.placed, new VParams() {
			@Override
			public void execute(Map<String, Object> map) {
				HashMapData hashmapdata = (HashMapData) map
						.get(AttachUtil.OBJECT);

				if (EntryPoint.game.getPersonMap().containsKey(name)) {
					hashmapdata.putLivingBeing(RandomPersonCreation
							.createPerson(EntryPoint.game.getPersonMap().get(
									name)));

				} else {
					RandomPersonCreation.addRandomPerson(hashmapdata,
							VConstants.human, GameUtil.getPlayerTeam());

				}
				ComplexCityGenerator.addEquipment(hashmapdata, type);
				hashmapdata.getLivingBeing().put(VConstants.owned, type);
				hashmapdata.getLivingBeing().getTemplate().getRationalMap()
						.put("sell", "sellone");

				hashmapdata.getLivingBeing().setTemplate(action);
				Item it = EntryPoint.game.getItem(VConstants.copper);
				it.setAmount(10);
				hashmapdata.getLivingBeing().getItemsCreate().add(it);
				bagMap.getBagMap().setData(bagMap.selected, null);
				bagMap.update();
				nextRandom();

			}
		}, data);

		shiftDown(data);

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
}
