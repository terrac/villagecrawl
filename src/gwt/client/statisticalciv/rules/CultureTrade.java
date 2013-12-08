package gwt.client.statisticalciv.rules;

import java.util.ArrayList;
import java.util.List;

import gwt.client.game.OCommand;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.PeopleRule;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;

public class CultureTrade implements PBaseRule {
	public CultureTrade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		
		
		// create a "coin" (just have a list of paleolithic items, and it could
		// change
		// as time changes

		// create a trader that does a round trip
		// it boosts the culture of the highest culture it is
		// coming from
		doCultureTrader(hmd);
		return true;
	}

	public static String getOverlay(HashMapData hmd) {
		String highestCultureName = Demographics.getHighestCultureName(hmd);
		if(highestCultureName == null){
			return null;
		}
		return DemographicRule.getSingleton().getType(VConstants.leader).getPBase(highestCultureName).getS(VConstants.overlay);
	}

	private void doCultureTrader(HashMapData hmd) {

		LivingBeing lb = RandomPersonCreation.addRandomPerson(hmd,
				VConstants.human, VConstants.human);
		lb.getAlterHolder().put(VConstants.weapon, new Item("Staff"));
		lb.getAlterHolder().put(VConstants.body, new Item("Robe"));// make fancy
		lb.getPopulation().put(VConstants.type, SConstants.merchant);
		lb.getPopulation().put(VConstants.size, 10.0);

		OObject.setCurrent(lb, new SimpleOObject() {
			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
			
				// find nearest friendly village (not on hatred list) with
				// an imbalance of coins (give points to largest imbalance,
				// minus distance
				// (each village generates its own "coin", traders seek to
				// balance the coins
				// evently
				// initially just build a list and randomly pull from it
				final HashMapData home = MysticalQuest.getHome(person);
				final List<HashMapData> otherVillages = new ArrayList<HashMapData>();

				fullMapData.getNearby(person, new GetForNearby<HashMapData>(
						fullMapData) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						if (home.equals(hashmapdata)) {
							return null;
						}

						if (!hashmapdata.isBlock()
								&& DemographicRule.isVillage(hashmapdata)) {//
							otherVillages.add(hashmapdata);
						}
						return null;
					}
				}, 8);
				HashMapData hmd = VConstants.getRandomFromList(otherVillages);
				if (hmd != null) {
					OobList oobList = new OobList(new Move(hmd, "trade"),
							new Wait("happy", 1), new Move(home, "tradeHome"),new RemovePerson());
					put(VConstants.overlay, CultureTrade.getOverlay(hmd));
					addToList(person, oobList);
					
					String cultureName = Demographics
							.getHighestCultureName(home);
					Demographics demo = DemographicRule.getDemo(hmd);
					Demographics.addCulture(demo, cultureName, .05);
//					if(Demographics.getCulture(demo).getDouble(cultureName) > .5){
//						demo.put(VConstants.leader, DemographicRule.getDemo(home).get(VConstants.leader));
//					}
				} else {
					person.death();
				}

				return new Returnable(true);
			}
		});

	}
}
