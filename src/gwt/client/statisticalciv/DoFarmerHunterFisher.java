//package gwt.client.statisticalciv;
//
//import gwt.client.EntryPoint;
//import gwt.client.game.display.UImage;
//import gwt.client.game.vparams.DisplayPopup;
//import gwt.client.game.vparams.random.RandomPersonCreation;
//import gwt.client.item.Item;
//import gwt.client.item.SimpleMD;
//import gwt.client.main.Move;
//import gwt.client.main.MoveRandomHashMapData;
//import gwt.client.main.PTemplate;
//import gwt.client.main.Point;
//import gwt.client.main.Returnable;
//import gwt.client.main.VConstants;
//import gwt.client.main.Wait;
//import gwt.client.main.base.LivingBeing;
//import gwt.client.main.base.OObject;
//import gwt.client.main.base.OobList;
//import gwt.client.main.base.PBase;
//import gwt.client.main.base.Parameters;
//import gwt.client.main.base.SimpleOObject;
//import gwt.client.map.FullMapData;
//import gwt.client.map.HashMapData;
//import gwt.client.map.MapData;
//import gwt.client.map.runners.GetForNearby;
//import gwt.client.statisticalciv.oobjects.RemovePerson;
//import gwt.client.statisticalciv.oobjects.TechnologyAction;
//import gwt.shared.ClientBuild;
//import gwt.shared.StatisticalCiv;
//import gwt.shared.datamodel.VParams;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gwt.user.client.Window;
//
//public class DoFarmerHunterFisher extends VParams {
//
//	public DoFarmerHunterFisher() {
//	}
//
//	boolean init;
//
//	public void addTemplate(FullMapData fmd) {
//		if (init) {
//			return;
//		}
//		init = true;
//
//		int count = TechnologyRule.getDefaultInt(SConstants.people, 1);
//		for (HashMapData hmd : fmd) {
//			if (count > 0) {
//				LivingBeing lb = addPerson();
//				FullMapData.addRandomSpot(fmd, lb);
//
//				count--;
//			}
//		}
//
//	}
//
//	static List<LivingBeing> mainPeople = new ArrayList<LivingBeing>();
//	int nextTech;
//
//	@Override
//	public void execute(Map<String, Object> map) {
//		FullMapData fmd = getFMD(map);
//		
//		addTemplate(fmd);
//		if(mainPeople.size() == 0){
//			Window.alert("no more people");
//		}
//		// a check techs against turns and randomly assign tech if ready
//
//		if (nextTech >= EntryPoint.game.getTurn()) {
//			List<PBase> techList = EntryPoint.game.getTechnology().getList(
//					VConstants.list);
//			for (PBase p : techList) {
//				int turn = p.getInt(VConstants.turn);
//				if (turn <= EntryPoint.game.getTurn()) {
//					// add tech to one person
//					LivingBeing lb = VConstants
//							.getRandomFromList(mainPeople);
//					TechnologyRule.addTech(lb, p.getS(VConstants.name));
//				} else {
//					nextTech = turn;
//					break;
//				}
//			}
//		}
//		for (LivingBeing lb : fmd.people) {
//
//			// the oobject takes the current oob and holds it
//			if (DoFarmerHunterFisher.isHuman(lb)) {
////				String name = lb.getPopulation().getType(VConstants.culture)
////						.getS(VConstants.name);
////				lb.getParent().put(SConstants.territory, name);
//				lb.getStats().put(VConstants.health,1);
//
//			}
//		}
//		for (final LivingBeing mainPerson : mainPeople) {
//			if(mainPerson.getParent() == null){
//				mainPeople.remove(mainPerson);
//				break;
//			}
//			
//			int health = (int) mainPerson.getPopulation().getDouble(VConstants.size);
//			mainPerson.getStats().put(VConstants.health,Math.min(50, health));
//			mainPerson.getStats().put(VConstants.maxhealth, 50);
//
//			// decide when to split off things like shepherds
//			// and farmers
//			
//			// if .50
//			// add a person
//			// set their culture from main
//			// make them a hunter
//			// or if shepherd tech now exists, gradual % increase of becoming a
//			// shepherd
//			// starting at around 10% and going up to 90%
//
//			// if .50
//			// add a person
//			// set their culture from main
//			// make them a fisher
//			if (VConstants.getRandom().nextDouble() < .001) {		
//				LivingBeing per = addPerson();
//				fmd.getNearestEmpty(mainPerson).putAppropriate(per);
//				//can't add and loop
//				break;
//			}
//			
//			if (VConstants.getRandom().nextDouble() < .005) {		
//				VillageRule.doFarmer(fmd, mainPerson);
//			}
//			if (VConstants.getRandom().nextDouble() < .05&&waitBeforeAnotherDecision(mainPerson.getPopulation(), "foodg", 2)) {
//				if (VConstants.getRandom().nextBoolean()) {
//					if (true) {// check shepherd formula
//						
//						doHunter(fmd, mainPerson);
//						
//					} else {
//
//					}
//				} else {
//					doFisher(fmd, mainPerson);
//						
//
//				}
//			}
//
//		}
//
//	}
//
//	public void doFisher(FullMapData fmd, final LivingBeing mainPerson) {
//		final HashMapData hmd = fmd.getNearKeyValue(
//				VConstants.obstacle, "goldfish", mainPerson, 20);
//		if(hmd == null){
//			return;
//		}
//		LivingBeing person = RandomPersonCreation.addRandomPerson(
//				mainPerson.getParent(), VConstants.human, VConstants.human);
//		person.put(VConstants.population, new PBase());
//		
//		OobList olist = new OobList(VConstants.water,new Move(hmd, "fish",
//				VConstants.water), new Wait(10), new SimpleOObject() {
//
//			@Override
//			public Returnable execute(FullMapData fullMapData,
//					LivingBeing person) {
//				if (hmd != null) {
//					double popsize = PBase.getDouble(
//							person.getPopulation(), VConstants.size);
//					double pinc = popsize / 10 + 1;
//					int eat = (int) (2 + ((pinc) + pinc
//							* .5));
//					FoodRule.getFood(eat,
//							person.getType(VConstants.population),
//							pinc);
//					hmd.getMapData(VConstants.obstacle).getPBase(
//							VConstants.population);
//					person.getPopulation().put(VConstants.food, 5.0);
//					DisplayPopup displayPopup = new DisplayPopup(
//							ClientBuild.list(new UImage("")));
//				}
//				return null;
//			}
//		}
//		, new Move(mainPerson,
//						"movebackf"), new RemovePerson(mainPerson.getPopulation()));
//		OObject.setCurrent(person, olist);
//	}
//
//	
//	
//	public void doHunter(FullMapData fmd, final LivingBeing mainPerson) {
//		final HashMapData gotten = fmd.getNearby(mainPerson,
//				new GetForNearby<HashMapData>(fmd) {
//					@Override
//					public HashMapData get(
//							HashMapData hashmapdata) {
//						LivingBeing lb = hashmapdata
//								.getLivingBeing();
//						if (lb != null) {
//							if (isHuman(lb)) {
//								return null;
//							}
//
//							return hashmapdata;
//						}
//
//						return super.get(hashmapdata);
//					}
//
//				}, 8);
//		if(gotten == null){
//			return;
//		}
//		LivingBeing person = RandomPersonCreation
//				.addRandomPerson(mainPerson.getParent(),
//						VConstants.human, VConstants.human);
//		person.put(VConstants.population, new PBase());
//		person.getAlterHolder().put(VConstants.weapon, new Item("spear"));
//		final LivingBeing gottenPerson = gotten.getLivingBeing();
//		OobList olist = new OobList(VConstants.food,new Move(gotten, "fish",
//				VConstants.food), new SimpleOObject() {
//
//			@Override
//			public Returnable execute(FullMapData fullMapData,
//					LivingBeing person) {
//
//				Double pinc = getGrowth(person);
//				person.getPopulation().put(
//						SConstants.huntingsuccess, true);
////								if (gotten == null
////										|| !Point.nextTo(person, gotten)) {
////									person.getPopulation().put(
////											SConstants.huntingsuccess, false);
////									int food = 0;
////									if (PBase.getDefaultBoolean(this,
////											SConstants.gather, true)) {
////										food = 5;
////										MoveRandomHashMapData moveRandom = new MoveRandomHashMapData(
////												SConstants.gather);
////										moveRandom.put(VConstants.overlay,
////												"happy");
////										addToList(person, moveRandom);
////										return null;
////									}
////
////									int pop = person.getType(
////											VConstants.population).getInt(
////											VConstants.size);
////									FoodRule.getFood(food, person
////											.getType(VConstants.population),
////											pinc);
////									return null;
////								}
//				if(gotten.getLivingBeing() == null){
//					gottenPerson.put(VConstants.visualdamage, "damagesword");
//					
//					return null;
//				} else {
//					gottenPerson.put(VConstants.visualdamage, "damagebow");
//					
//				}
//				
//				PBase popg = gotten.getLivingBeing().getType(
//						VConstants.population);
//				Double popsize = PBase.getDouble(popg,
//						VConstants.size);
////								popsize += TechnologyRule.getDefaultInt(person,
////										SConstants.hunting, 0);
//				popsize = FoodRule.getFood(popsize,
//						person.getType(VConstants.population),
//						pinc);
//				popg.put(VConstants.size, popsize);
//				popg.put(VConstants.food, 5.0);
//				return null;
//			}
//
//		}
//
//		, new Move(mainPerson,
//				"movebackh"), new RemovePerson(mainPerson.getPopulation()));
//OObject.setCurrent(person, olist);
//	}
//
//	private List<LivingBeing> onlyHumans(List<LivingBeing> people) {
//		List<LivingBeing> l = new ArrayList<LivingBeing>();
//		for (LivingBeing lb : people) {
//			if (DoFarmerHunterFisher.isHuman(lb)) {
//				l.add(lb);
//			}
//		}
//		return l;
//	}
//
//	public static LivingBeing addPerson() {
//		LivingBeing lb = RandomPersonCreation.createPerson("human female");
//		mainPeople.add(lb);
//		lb.getType(VConstants.population).put(VConstants.size, (double) 10);
//		lb.getAlterHolder().put(VConstants.weapon, new Item("spear"));
//		lb.getAlterHolder().put(VConstants.body, new Item("animal skin"));
//		PBase.increment(lb.getPopulation(), VConstants.food, 20.0);
//		
//		OObject.setCurrent(lb, new SimpleOObject() {
//
//			@Override
//			public Returnable execute(FullMapData fullMapData,
//					LivingBeing person) {
//				MoveRandomHashMapData moveRandom = new MoveRandomHashMapData(
//						SConstants.gather);
//				moveRandom.put(VConstants.overlay, "happy");
//				OObject.addToList(person, moveRandom);
//				double food = person.getPopulation().getDouble(VConstants.food);
//				if(food > 0){
//					PBase.decrement(person.getPopulation(), VConstants.food, 1.0);
//					PBase.increment(person.getPopulation(), VConstants.size, 1.0);
//					
//				} else {
//					PBase.decrement(person.getPopulation(), VConstants.size, 1.0);
//					
//				}
//				if(person.getPopulation().getDouble(VConstants.size) < 0){
//					person.death();
//				}
//				return new Returnable(true);
//			}
//		});
//		
//		return lb;
//	}
//
//	public static boolean isHuman(LivingBeing lb) {
//		return VConstants.human.equals(lb.getTeam());
//	}
//
//	@Override
//	public PBase clone() {
//		return new DoFarmerHunterFisher().copyProperties(this);
//	}
//
//	public static PBase getPopulation(LivingBeing lb) {
//		return lb.getPBase(VConstants.population);
//	}
//
//	public static double getGrowth(LivingBeing person) {
//		String name = person.getType();
//		int maxsize = TechnologyRule.getDefaultInt(VConstants.person, name,
//				VConstants.maxsize, 50);
//		double growth = TechnologyRule.getDefaultInt(VConstants.person, name,
//				VConstants.growth, 4);
//		double size = PBase.getDouble(person.getPopulation(), VConstants.size);
//		double inefficency = size / maxsize;
//		growth = growth - inefficency * growth;
//		if (growth < 1) {
//			growth = 1;
//		}
//		return growth;
//
//	}
//	
//	public static boolean waitBeforeAnotherDecision(PBase city, String farm, int wait) {
//		int turn = EntryPoint.game.getTurn();
//		int waitturn = city.getInt("waitturn"+farm);
//		if(turn > waitturn + wait){
//			city.put("waitturn"+farm, turn);
//			return true;
//		}
//		return false;
//	}
//}
