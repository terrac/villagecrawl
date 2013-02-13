package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.VExpression;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class TradeCultureRoute extends VParams {

	public TradeCultureRoute() {

	}

	public TradeCultureRoute(int x, int y, int count, PBase... routes) {
		put(VConstants.xsymbolic, x);
		put(VConstants.ysymbolic, y);
		put(VConstants.count, count);
		put(VConstants.list, Arrays.asList(routes));
	}

	int c2;

	@Override
	public void execute(Map<String, Object> map) {
		//PBase pb=(PBase)map.get(AttachUtil.OBJECT);
		
		if (c2 < getInt(VConstants.count)) {
			c2++;
			return;
		}
		String template = "haggle";
		if (VConstants.getRandom().nextDouble() > .15) {
			// further break into buy and sell here
			template = "backgroundbuy";
		}
		doTrade(template);

	}

	public void doTrade(String template) {
		FullMapData fullMapData = EntryPoint.game
				.getMapArea()
				.getMap()
				.getData(getInt(VConstants.xsymbolic),
						getInt(VConstants.ysymbolic));

		// add trader

		PBase route = (PBase) VConstants
				.getRandomFromList(getList(VConstants.list));

		if(route.getB(VConstants.disabled)){
			return;
		}
		
		String name = route.getS(VConstants.race);
		String gender = VConstants.male;
		if (VConstants.getRandom().nextBoolean()) {
			gender = VConstants.female;
		}
		LivingBeing lb = addMon(fullMapData, route.getS(VConstants.start),
				(String) name + " " + gender, VConstants.neutral);
		lb.put(VConstants.economy, null);

		

		lb.getTemplate().setRationalChild("main", template);

		lb.put(VConstants.exit, route.getS(VConstants.exit));
		ComplexCityGenerator.addEquipment(lb.getParent(), name);
		Item cop = EntryPoint.game.getItem(VConstants.copper);
		cop.setAmount(10);
		lb.getItemsCreate().add(cop);
		c2 = 0;
	}

	public static LivingBeing addMon(FullMapData fullMapData, String value,
			String monname, String team) {
		LivingBeing lb = RandomPersonCreation.createPerson(monname);
		lb.setTeam(team);
		// lb.put(VConstants.image, "/images/goblin.png");
		// lb.getTemplate().setRationalChild("main", "recruitable");
		HashMapData allKeyValue = fullMapData.getAllKeyValue(VConstants.gate,
				value);
		if (allKeyValue == null) {
			Window.alert(value + " is missing");
		}
		HashMapData nearestEmpty = fullMapData.getNearestEmpty(allKeyValue);
		if (nearestEmpty != null) {

			nearestEmpty.putAppropriate(lb);
		}
		return lb;
	}


	@Override
	public PBase clone() {
		
		TradeCultureRoute tcr = new TradeCultureRoute().copyProperties(this);
		tcr.c2 = tcr.getInt(VConstants.count);
		
		return tcr;
	}

}
