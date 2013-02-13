package gwt.client.game.vparams.quest;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.oobjects.Sickness;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.TradeCultureRoute;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.requirements.TwoOptionEvent;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

public class ComplexCityGenerator extends VParams {

	public ComplexCityGenerator() {
	}

	public ComplexCityGenerator(String state,PBase... stats) {
		// PBase pb = new PBase();
		// setPBase(pb, stats);
		// put(VConstants.stats, pb);
		put(VConstants.item,state);
		put(VConstants.list, Arrays.asList(stats));
	}

	int personCount = 0;
	public static int cDone = 0;
	boolean first = true;
	int lastGDPBread = 0;

	int bcount = 0;
	int waitcount = 0;
	TradeCultureRoute tcr = new TradeCultureRoute(1, 1 ,80);

	//String stage = "storycreate";
	String stage = null;
	private int addedRadius;

	@Override
	public void execute(Map<String, Object> map) {
		
		if(stage == null){
			stage = getS(VConstants.item);
		}
		int gdp = EntryPoint.game.getInt(VConstants.GDP);
		List<PBase> pbList = getList(VConstants.list);
		final FullMapData fmd = EntryPoint.game.getHtmlOut().currentFMD;
		BagMap bagMap = (BagMap) EntryPoint.game.get(VConstants.bagmap);
		List<VParams> vpL = EntryPoint.game.getPBase(VConstants.culture)
				.getList(VConstants.storyevent);

		
		
//		if(VConstants.getRandom().nextInt(200) > 5){
//			runLines(map);
//
//		}
		HashMapData hmd = getRandom(fmd);

		if (hmd != null) {
			String owned = hmd.getS(VConstants.owned);
			if (owned != null) {
				Item item=createItem(owned);
				hmd.getItemsCreate().add(item.clone());
				
			}
		}

		if (waitcount > 0) {
			waitcount--;
			return;
		}

		if ("firstmap".equals(stage)) {
//			createRandomSetup(pbList, fmd);
//			personChoice(fmd);
//			RandomEffects.addBuilding("barn", "farms", null,fmd);
//			RandomEffects.addBuilding("woodcutter", "resourcegather", null,fmd);
//			RandomEffects.addBuilding( "bakery", "farms", null,fmd);
			//stage = "2";
			((VParams) EntryPoint.game.get(VConstants.randomeffect)).execute(null);
			waitcount = 60000;
			return;
		}
		
		
		if ("2".equals(stage)) {
			tcr.execute(map);
			stage = "4";
			waitcount = 30;
			return;
		}

		if ("1".equals(stage)) {
			addTradePersonCulture(hmd);
			addTradePersonCulture(hmd);
			//runLines(map);
			// createRandomSetup(pbList, fmd);
			//addBuilding(bagMap, "bakery", "farms");
			stage = "2";
			waitcount = 60000;
			return;
		}

		if ("4".equals(stage)) {
			vpL.get(0).execute(map);
			vpL.remove(0);
			stage = "5";
			waitcount = 30000;
			return;
		}

		if ("5".equals(stage)) {
			tcr.execute(map);
			stage = "6";
			waitcount = 30;
			return;
		}

		if ("6".equals(stage)) {
			tcr.execute(map);
			stage = "7";
			waitcount = 30;
			return;
		}

		if ("7".equals(stage)) {
			RandomEffects.addBuilding("blacksmith", "guard",null,fmd,10);
			stage = "8";
			waitcount = 30;
			return;
		}
		if ("8".equals(stage)) {
			illEffect(fmd, hmd);
			waitcount = 30;
			stage = "9";
			return;
		}
		
		if ("storycreate".equals(stage)) {
			createSpecificSetup(pbList, fmd);
			stage = "story";
			waitcount = 30;
			return;
		}
		if ("story".equals(stage)) {
			vpL.get(0).execute(map);
			vpL.remove(0);
			//stage = "5";
			waitcount = 100;
			return;
		}
		
		PBase type = EntryPoint.game.getType(VConstants.culture).getType(
				VConstants.stats);
		if (type.getInt(VConstants.economy) / 5 != addedRadius) {
			addedRadius = type.getInt(VConstants.economy) / 5;
			for (LivingBeing lb : fmd.people) {
				if ("gatherowneditem".equals(lb.getTemplate().getRationalMap()
						.getS(VConstants.main))) {
					lb.setTemplate("farms");
				}
			}
		}

		if ("9".equals(stage)) {
			PBase pb = VConstants.getRandomFromList(pbList);
			RandomEffects.addBuilding(pb.getS(VConstants.type),
					pb.getS(VConstants.action),null,fmd,10);
			waitcount = 30;
			// stage = "10";
			return;
		}
		// if (bcount == 0) {
		// PBase pb = VConstants.getRandomFromList(pbList);
		// addBuilding(bagMap, pb.getS(VConstants.type),
		// pb.getS(VConstants.action));
		//
		// }
		//
		// bagMap.update();

	}

	public static Item createItem(String owned) {
		String itemtype = EntryPoint.game.getPBase(VConstants.culture)
				.getPBase(VConstants.itemmap).getS(owned);
		List list = EntryPoint.game.getPBase(VConstants.culture)
				.getType(VConstants.items).getType(itemtype)
				.getList(VConstants.list);
		if (list == null) {
			Window.alert(owned + " has no things");
			return null;
		} else {
			Item item = (Item) VConstants.getRandomFromList(list);
			item = item.clone();
			System.out.println(item.getAmount());
			item.put(owned, true);
			return item;
		}
		
	}

	public void runLines(Map<String, Object> map) {
		List<VParams> rL = EntryPoint.game.getPBase(VConstants.culture)
				.getList(VConstants.lines);
		for(VParams v : rL){
			v.execute(map);
		}
	}

	public void createRandomSetup(List<PBase> pbList, final FullMapData fmd) {
		for (int a = 0; a < 10; a++) {
			HashMapData h = getRandom(fmd);
			PBase pb = VConstants.getRandomFromList(pbList);

			final String type = pb.getS(VConstants.type);
			final String action = pb.getS(VConstants.action);
			addBuilding(action, type, h);
		}
	}

	public void addBuilding(String action, String type, HashMapData h){
		SimpleMD data = new SimpleMD(VConstants.gate, type);
		RandomPersonCreation.addRandomPerson(h, VConstants.human,
				GameUtil.getPlayerTeam());
		if (h.getLivingBeing() == null) {
			return;
		}
		h.getLivingBeing().put(VConstants.owned, type);
		h.getLivingBeing().setTemplate(action);
		Item ite = EntryPoint.game.getItem("wood");
		ite.setAmount(6);
		h.getLivingBeing().getItemsCreate().add(ite);
		
		List<String> l = EntryPoint.game.getPBase(VConstants.culture)
				.getPBase(VConstants.equipment).getList(type);
		if(l != null){
			for (String b : l) {

				Item it = EntryPoint.game.getItem(b);
				if (it == null) {
					System.out.println(b);
					continue;
				}
				h.getLivingBeing().getAlterHolder().put(it.getType(), it);
			}
		}

		h.put(VConstants.gate, data);

	}
	public void createSpecificSetup(List<PBase> pbList, final FullMapData fmd) {
		PBase bakery = new PBase(VConstants.action, "farms", VConstants.type,
				"bakery");

		PBase barn = new PBase(VConstants.action, "farms", VConstants.type,
				"barn");
		
		for (int a = 0; a < 6; a++) {
			HashMapData h = getRandom(fmd);
			PBase pb =bakery;
			if(VConstants.getRandom().nextBoolean()){
				pb = barn;
			}

			final String type = pb.getS(VConstants.type);
			final String action = pb.getS(VConstants.action);
			SimpleMD data = new SimpleMD(VConstants.gate, type);
			RandomPersonCreation.addRandomPerson(h, VConstants.human,
					GameUtil.getPlayerTeam());
			if (h.getLivingBeing() == null) {
				continue;
			}
			h.getLivingBeing().put(VConstants.owned, type);
			h.getLivingBeing().setTemplate(action);
			Item ite = EntryPoint.game.getItem("wood");
			ite.setAmount(12);
			h.getLivingBeing().getItemsCreate().add(ite);
			addEquipment(h, type);
			h.put(VConstants.gate, data);
		}
	}

	public static void addEquipment(HashMapData h, final String type) {
		List<String> l = EntryPoint.game.getPBase(VConstants.culture)
				.getPBase(VConstants.equipment).getList(type);
		if(l == null){
			l =h.getLivingBeing().getList(VConstants.equipment);
		}
		if(l == null){
			l =Arrays.asList(new String[]{"robe"});
		}
		if(l == null){
			return;
		}
		for (String b : l) {

			Item it = EntryPoint.game.getItem(b);
			if (it == null) {
				continue;
			}
			h.getLivingBeing().getAlterHolder().put(it.getType(), it);
		}
	}

	public void illEffect(final FullMapData fmd, HashMapData hmd) {
		int cityhealth = EntryPoint.game.getType(VConstants.culture)
				.getType(VConstants.stats).getInt(VConstants.health);

		if (VConstants.getRandom().nextInt(30) > 15 - cityhealth) {

			if (sickness(fmd, cityhealth)) {
				return;
			}
		}

		if (VConstants.getRandom().nextBoolean()) {
			RandomPersonCreation.addToMap(hmd,
					RandomPersonCreation.createPerson("wolf"));
			return;
		}

		addMafia(hmd);
	}

	public void addMafia(HashMapData hmd) {
		EntryPoint.game.getHtmlOut().addGDP(-10);
		RandomPersonCreation.addRandomPerson(hmd, "human",
				GameUtil.getPlayerTeam());
		hmd.getLivingBeing().put(VConstants.image, "/images/sonja.png");
		hmd.getLivingBeing().put(VConstants.dontdrawequipment, true);
		hmd.getLivingBeing().setTemplate("mafioso");
	}

	public boolean sickness(final FullMapData fmd, int cityhealth) {
		for (LivingBeing b : fmd.people) {
			if (VConstants.getRandom().nextDouble() < .1) {
				b.getStats().put(VConstants.sickness, 30 + -cityhealth);
				OObject.addToList(b, new Sickness());

				return true;
			}
		}
		return false;
	}

	public static HashMapData getRandom(FullMapData fmd) {
		int xrand = VConstants.getRandom().nextInt(fmd.getXsize());
		int yrand = VConstants.getRandom().nextInt(fmd.getYsize());
		HashMapData hmd = fmd.getData(xrand, yrand);
		return hmd;
	}
	public void addTradePersonCulture(HashMapData hashMapData){
		hashMapData =hashMapData.getParent().getData(1, 1);
		hashMapData = hashMapData.getParent().getNearestEmpty(hashMapData);
		List list = EntryPoint.game.getPBase(VConstants.culture).getList(VConstants.quest);
		PBase pb=(PBase)VConstants.getRandomFromList(list);
		list.remove(pb);
		hashMapData.putLivingBeing(RandomPersonCreation
				.createPerson(pb.getPBase(VConstants.person)));
		hashMapData.getLivingBeing().put(VConstants.quest, pb);
		hashMapData.getLivingBeing().setTemplate("specialhaggle");
		addEquipment(hashMapData,pb.getS(VConstants.type));
	}


	public void personChoice(FullMapData fmd){
		List<PBase> people= EntryPoint.game.getPBase(VConstants.culture).getPBase(VConstants.person).getList(VConstants.list);
		
		//2 the complex picks a type and two people
		PBase pb=VConstants.getRandomFromList(people);
		PBase pb2=VConstants.getRandomFromList(people);
//		if(pb == pb2){
//			return;
//		}
		LivingBeing lb=VConstants.getRandomFromList(fmd.people);
		
		String type=lb.getType();
		
		//3 it displays the choice
		VParams vp=new TwoOptionEvent("For "+type+" choose either "+pb.getS(VConstants.lines)+" or "+ pb2.getS(VConstants.lines),null,null);
		//vp.execute(null);
		//#4 culture has a list of types pointing to stats pointing to stat changes

	}
	
	@Override
	public PBase clone() {

		return new ComplexCityGenerator().copyProperties(this);
	}
	
	

}
