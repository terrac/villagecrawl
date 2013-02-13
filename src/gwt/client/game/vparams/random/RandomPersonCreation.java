package gwt.client.game.vparams.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.rpc.core.java.util.Collections;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.item.Item;
import gwt.client.main.Game;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;
import gwt.server.rpc.gets.GetSaveGame;
import gwt.shared.ClientBuild;
import gwt.shared.ClientBuild2;
import gwt.shared.datamodel.VParams;

public class RandomPersonCreation extends VParams implements RandomCreation {

	// initially no random elements
	public RandomPersonCreation() {

	}

	public RandomPersonCreation(String team,String type,Object ... list) {
		put(VConstants.team,team);
		put(VConstants.type,type);
		
		put(VConstants.list, new ArrayList(Arrays.asList(list)));
	}


	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmd= (HashMapData) map.get(AttachUtil.OBJECT);

		Object obj = map.get(VConstants.item);
		LivingBeing lb;
		if(obj instanceof PBase){
			lb=createPerson((PBase)obj);
		} else {
			lb=createPerson((String)obj);
		}
		lb.setTeam(getS(VConstants.team));
		map.put(VConstants.item, lb);
		//calculate from value of monsters
		map.put(VConstants.value, 50);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gwt.client.game.vparams.random.RandomCreation#randomize(gwt.client.map
	 * .FullMapData)
	 */
	public MapData randomize(List<String> categories) {

		List<String> list = getList(VConstants.list);
		if (list != null) {
			String name = VConstants.getRandomFromList(list);
			return createPerson(categories, getS(VConstants.team),
					VConstants.livingbeing + " " + name);

		}

		// List<String> types = getList(VConstants.type);

		List<String> claa = null;
		List<String> types = null;
		PBase assocCat = EntryPoint.game.getPBase(VConstants.person).getPBase(
				VConstants.type);
		if (categories == null) {
			claa = getList(VConstants.classes);
			types = getList(VConstants.type);
		} else {
			claa = CategoryUtil.getAssoc(
					VConstants.getRandomFromList(categories), assocCat);
		}

		String claname = VConstants.getRandomFromList(claa);
		String typename = VConstants.getRandomFromList(types);

		LivingBeing lb = createPerson(categories, getS(VConstants.team),
				VConstants.livingbeing + " " + typename + " " + claname);

		// return lb

		// String type = types.get(game.getRandom().nextInt(types.size()));
		// LivingBeing lb=game.getPersonMap().get(type).clone();
		//
		return lb;
	}

	public static LivingBeing createPerson(String traitnames) {
		if (traitnames.startsWith(VConstants.livingbeing)) {
			traitnames = traitnames
					.substring(VConstants.livingbeing.length() + 1);
		}
		String[] tarr = traitnames.split(" ");

		PBase person = EntryPoint.game.getPBase(VConstants.person);

		
		
		
		PBase templateMap = EntryPoint.game.getPBase(VConstants.person)
				.getPBase(VConstants.templatemap);

		LivingBeing lb = (LivingBeing) person.get(VConstants.livingbeing);

		lb = lb.clone();
		lb.getAlterHolder();
		for (String key : tarr) {

			for (Entry<String, Object> eo : templateMap.getObjMap().entrySet()) {
				if (eo.getValue() instanceof PBase) {
					PBase pb = (PBase) ((PBase) eo.getValue()).get(key);
					if (pb != null) {
						String key2 = eo.getKey();
						transfer(lb, key2, pb);

						break;
					}
				}
			}

		}

		// the canonical living being

		lb.getType(VConstants.attributes).put(
				VConstants.personality,
				VConstants.getRandomFromList(person
						.getList(VConstants.personality)));
		lb.getType(VConstants.attributes).put(
				VConstants.intelligence,
				VConstants.getRandomFromList(person
						.getList(VConstants.intelligence)));

		transfer(lb, VConstants.traits,
				(PBase) VConstants.getRandomFromPBase(templateMap
						.getPBase(VConstants.traits)));
		setTemplateBasedOnMap(lb);

		if (tarr.length > 1) {
			lb.setType(tarr[0] + " " + tarr[1]);
		} else {
			lb.setType(traitnames);
		}

		lb.setTeam(tarr[0]);
		// generateRandomItems(lb);
		doHair(lb);
		
		if(tarr.length > 1){
			lb.put(VConstants.name,
					VConstants.getRandomFromList(person.getPBase(VConstants.name).getList(tarr[1])));
				
		}
		
		return lb;
	}

	public static LivingBeing createPerson(PBase prep) {
		String traitnames = prep.getS(VConstants.traits);

		String[] tarr = traitnames.split(" ");

		PBase person = EntryPoint.game.getPBase(VConstants.person);
		PBase templateMap = EntryPoint.game.getPBase(VConstants.person)
				.getPBase(VConstants.templatemap);

		LivingBeing lb = (LivingBeing) person.get(VConstants.livingbeing);
		lb = lb.clone();

		for (String key : tarr) {

			for (Entry<String, Object> eo : templateMap.getObjMap().entrySet()) {
				if (eo.getValue() instanceof PBase) {
					PBase pb = (PBase) ((PBase) eo.getValue()).get(key);
					if (pb != null) {
						String key2 = eo.getKey();
						transfer(lb, key2, pb);

						break;
					}
				}
			}

		}

		lb.setTeam(tarr[0]);

		lb.put(VConstants.name, prep.get(VConstants.name));
		lb.getAttributes().put(VConstants.intelligence,
				prep.get(VConstants.intelligence));
		lb.put(VConstants.image, prep.get(VConstants.image));
		if (prep.containsKey(VConstants.dontdrawequipment)) {
			lb.put(VConstants.dontdrawequipment,
					prep.get(VConstants.dontdrawequipment));
		}

		if (prep.containsKey(VConstants.team)) {
			lb.put(VConstants.team, prep.get(VConstants.team));
		}

		setTemplateBasedOnMap(lb);

		if (prep.containsKey(VConstants.templatemap)) {

			lb.getTemplate().put(VConstants.templatemap,
					prep.get(VConstants.templatemap));
		}

		if (tarr.length > 1) {
			lb.setType(tarr[0] + " " + tarr[1]);
		} else {
			lb.setType(traitnames);
		}
		// generateRandomItems(lb);

		setIfContains(prep, lb, VConstants.hair);
		setIfContains(prep, lb, VConstants.face);
		setIfContains(prep,lb, VConstants.avatar);
		setIfContains(prep,lb, VConstants.equipment);
		setIfContains(prep,lb, VConstants.items);
		setIfContains(prep,lb, VConstants.owned);
		setIfContains(prep, lb.getTemplate().getRationalMap(), VConstants.personality);
		doHair(lb);
		
		
		return lb;

	}

	public static void doHair(LivingBeing lb) {
		if (!lb.getB(VConstants.dontdrawequipment)&&!lb.containsKey(VConstants.hair)) {
			String rand = VConstants.getRandomFromList(hair);
			lb.put(VConstants.hair, rand);
		}
	}

	static List<String> hair = Arrays.asList(new String[] { "arwen-hair",
			"short_black", "fem_red", "brown-hair" });
	static String[] iArr = new String[] { VConstants.body, VConstants.leg };
	static String[] invA = new String[] { VConstants.cloak, VConstants.body,
			VConstants.leg, VConstants.gloves, VConstants.weapon };

	public static void generateRandomItems(LivingBeing lb) {
		if (!lb.getB(VConstants.humanoid)) {
			return;
		}
		List<String> list = (List<String>) lb.getList(VConstants.equipment);
		if (list != null) {
			for (String si : list) {
				Item it = EntryPoint.game.getItemMap().get(si).clone();
				lb.getAlterHolder().put(it.getS(VConstants.type), it);
			}
		}

		for (String a : iArr) {
			RandomItemCreation ric = (RandomItemCreation) EntryPoint.game
					.get(VConstants.randomitem);
			Item item = ric.randomize(a);
			lb.getAlterHolder().put(a, item);

		}

		if (!lb.getAlterHolder().containsKey(VConstants.body)) {
			lb.getAlterHolder().put(VConstants.body,
					EntryPoint.game.getItemMap().get("animal skin"));
		}

		{
			String rand = VConstants.getRandomFromList(Arrays.asList(invA));
			RandomItemCreation ric = (RandomItemCreation) EntryPoint.game
					.get(VConstants.randomitem);
			Item item = ric.randomize(rand);
			lb.getItemsCreate().put(item);

		}
		{
			String rand = VConstants.getRandomFromList(Arrays.asList(invA));
			RandomItemCreation ric = (RandomItemCreation) EntryPoint.game
					.get(VConstants.randomitem);
			Item item = ric.randomize(rand);
			lb.getItemsCreate().put(item);

		}
		{
			String rand = VConstants.getRandomFromList(Arrays.asList(invA));
			RandomItemCreation ric = (RandomItemCreation) EntryPoint.game
					.get(VConstants.randomitem);
			Item item = ric.randomize(rand);
			lb.getItemsCreate().put(item);

		}

		{
			RandomItemCreation ric = (RandomItemCreation) EntryPoint.game
					.get(VConstants.randomitem);
			Item item = ric.randomize(VConstants.money);
			item.setAmount(VConstants.getRandom().nextInt(5) + 5);
			lb.getItemsCreate().put(item);

		}
		// get the default random item creation (this object does the rest)

		// get a list of all the item lists

		// try a couple from each list, if above value then reject
	}

	public static void transfer(LivingBeing lb, String atname, PBase pb) {
		lb.getType(VConstants.attributes).put(atname, pb.getS(VConstants.name));
		Stats st = lb.getStats();
		add(pb, st, VConstants.damage);
		add(pb, st, VConstants.armor);
		add(pb, st, VConstants.speed);
		add(pb, st, VConstants.intelligence);

		setIfContains(pb, lb, VConstants.humanoid);
		setIfContains(pb, lb, VConstants.wealth);
		setIfContains(pb, st, VConstants.defaultattack);
		setIfContains(pb, lb, VConstants.image);
		setIfContains(pb, st, VConstants.maxhealth);
	}

	private static void setIfContains(PBase pb, PBase st, String defaultattack) {
		if (pb.containsKey(defaultattack)) {
			st.put(defaultattack, pb.get(defaultattack));
		}
	}

	public static void setTemplateBasedOnMap(LivingBeing lb) {
		PBase templateMap = EntryPoint.game.getPBase(VConstants.templatemap);
		if (templateMap == null) {
			return;
		}
		String ttype = templateMap.getS(VConstants.type);
		if (ttype != null) {
			PBase stm = templateMap.getPBase(ttype);

			if (stm == null) {
				templateMap.put(VConstants.disabled, true);
			} else {
				templateMap.put(VConstants.disabled, false);
				// lb.getTemplate().getRationalMap().getObjMap().clear();
				String intel = lb.getAttributes().getS(VConstants.intelligence);

				lb.getTemplate().setRationalChild("main",
						templateMap.getPBase(ttype).getS(intel));
			}

		}
	}

	public static LivingBeing createPerson(List<String> categories,
			String team, String name) {
		LivingBeing lb = createPerson(name);
		if (lb != null && team != null) {
			lb.setTeam(team);
		}
		return lb;
	}

	private static void add(PBase clapb, Stats st, String strength) {

		st.put(strength,
				new Integer(
						(int) (st.getInt(strength) + clapb.getInt(strength))));

	}

	public static LivingBeing addRandomPerson(HashMapData hmd, String monname,
			String team) {
		String gender = VConstants.male;
		if (VConstants.getRandom().nextBoolean()) {
			gender = VConstants.female;
		}

		LivingBeing lb = RandomPersonCreation.createPerson(monname + " "
				+ gender);
		lb.setTeam(team);
		// lb.put(VConstants.image, "/images/goblin.png");
		// lb.getTemplate().setRationalChild("main", "recruitable");
		addToMap(hmd, lb);
		return lb;
	}

	public static void addToMap(HashMapData hmd, LivingBeing lb) {
		HashMapData nearestEmpty = hmd.getParent().getNearestEmpty(hmd);
		if (nearestEmpty != null) {

			nearestEmpty.putAppropriate(lb);
		}
	}

	@Override
	public PBase clone() {

		return new RandomPersonCreation().copyProperties(this);
	}

}
