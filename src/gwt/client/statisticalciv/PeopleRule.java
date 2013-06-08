package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

public class PeopleRule extends VParams {

	public PeopleRule() {
	}

	boolean init;

	public void addTemplate(FullMapData fmd) {
		if (init) {
			return;
		}
		init = true;

		int count = TechnologyRule.getDefaultInt(SConstants.people, 1);
		for (HashMapData hmd : fmd) {
			if (count > 0) {
				LivingBeing lb = addPerson();
				FullMapData.addRandomSpot(fmd, lb);

				count--;
			}
		}

	}

	static List<LivingBeing> mainPeople = new ArrayList<LivingBeing>();
	int nextTech;

	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);

		addTemplate(fmd);

		// a check techs against turns and randomly assign tech if ready

		if (nextTech >= EntryPoint.game.getTurn()) {
			List<PBase> techList = EntryPoint.game.getTechnology().getList(
					VConstants.list);
			for (PBase p : techList) {
				int turn = p.getInt(VConstants.turn);
				if (turn <= EntryPoint.game.getTurn()) {
					// add tech to one person
					LivingBeing lb = VConstants
							.getRandomFromList(mainPeople);
					TechnologyRule.addTech(lb, p.getS(VConstants.name));
				} else {
					nextTech = turn;
					break;
				}
			}
		}
		for (LivingBeing lb : fmd.people) {

			// the oobject takes the current oob and holds it
			if (PeopleRule.isHuman(lb)) {
//				String name = lb.getPopulation().getType(VConstants.culture)
//						.getS(VConstants.name);
//				lb.getParent().put(SConstants.territory, name);

			}
		}
		for (final LivingBeing mainPerson : mainPeople) {
			// decide when to split off things like shepherds
			// and farmers
			
			// if .50
			// add a person
			// set their culture from main
			// make them a hunter
			// or if shepherd tech now exists, gradual % increase of becoming a
			// shepherd
			// starting at around 10% and going up to 90%

			// if .50
			// add a person
			// set their culture from main
			// make them a fisher
			if (VConstants.getRandom().nextDouble() < .10) {		
				LivingBeing per = addPerson();
				fmd.getNearestEmpty(mainPerson).putAppropriate(per);
			}
			
			if (VConstants.getRandom().nextDouble() < .10) {		
				doFarmer(fmd, mainPerson);
			}
			if (VConstants.getRandom().nextDouble() < .10) {
				if (VConstants.getRandom().nextBoolean()) {
					if (true) {// check shepherd formula
						
						doHunter(fmd, mainPerson);
						
					} else {

					}
				} else {
					doFisher(fmd, mainPerson);
						

				}
			}

		}

	}

	public void doFisher(FullMapData fmd, final LivingBeing mainPerson) {
		final HashMapData hmd = fmd.getNearKeyValue(
				VConstants.obstacle, "goldfish", mainPerson, 20);
		if(hmd == null){
			return;
		}
		LivingBeing person = RandomPersonCreation.addRandomPerson(
				mainPerson.getParent(), VConstants.human, VConstants.human);
		person.put(VConstants.population, new PBase());
		
		OobList olist = new OobList(VConstants.water,new Move(hmd, "fish",
				VConstants.water), new Wait(10), new SimpleOObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				if (hmd != null) {
					double popsize = PBase.getDouble(
							person.getPopulation(), VConstants.size);
					double pinc = popsize / 10 + 1;
					int eat = (int) (2 + ((pinc) + pinc
							* TechnologyRule
									.getDefaultDouble(
											SConstants.fishingEffectiveness,
											VConstants.size, .5)));
					FoodRule.getFood(eat,
							person.getType(VConstants.population),
							pinc);
					hmd.getMapData(VConstants.obstacle).getPBase(
							VConstants.population);

				}
				return null;
			}
		}
		, new Move(mainPerson,
						"movebackf"), new RemovePerson());
		OObject.setCurrent(person, olist);
	}

	public void doFarmer(FullMapData fmd, final LivingBeing mainPerson) {
		HashMapData hmd=fmd.getNearKeyValue(VConstants.gate, SConstants.farm, mainPerson.getPosition(), 10);
		if(hmd == null){
			hmd = mainPerson.getParent();
		}
		final HashMapData farm=fmd.getNearby(mainPerson.getParent(), new GetForNearby<HashMapData>(fmd) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.isBlock()) return null;
				
				if(hashmapdata.containsKey(VConstants.gate)){
					MapData pfarm = hashmapdata.getMapData(VConstants.gate);
					if(pfarm.getValue().equals(SConstants.farm)){
						PBase pop = pfarm.getType(VConstants.population);
						if(pop.getDouble(VConstants.size)< 50){
							return hashmapdata;
						}
					}
					return null;
				}
				return hashmapdata;
			}
		}, 4);
		if(farm == null){
			return;
		}
		LivingBeing lb=RandomPersonCreation.addRandomPerson(mainPerson.getParent(), VConstants.human, VConstants.human);
		lb.getAlterHolder().put(VConstants.weapon, new Item("scythe"));
		OObject.setCurrent(lb, new Move((HashMapData) farm,"createfarm"));
		OObject.addToList(lb, new SimpleOObject() {
			
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				MapData mdFarm = farm.getMapData(VConstants.gate);
				if(mdFarm != null){
					PBase.increment(mdFarm.getType(VConstants.population), VConstants.size, 3);
					person.death();
					return null;
				}
				farm.remove("wheat");
				farm.getItemsCreate().clear();
				farm.put("growth", 0);
				SimpleMD md = new SimpleMD(VConstants.gate, "farm");
				farm.put(md);
				//person.getPopulation().put(VConstants.home, farm.getPosition());
				md.put(VConstants.population,person.getPopulation());
//				farmPops.add(person.getPopulation());
//				List<MapData> pbl=cityFarmMap.get(cityH);
//				if(pbl == null){
//					pbl = new ArrayList();
//					cityFarmMap.put(cityH, pbl);
//				}
//				pbl.add(md);
				person.death();
				return null;
			}
		});
	}

	
	public void doHunter(FullMapData fmd, final LivingBeing mainPerson) {
		final HashMapData gotten = fmd.getNearby(mainPerson,
				new GetForNearby<HashMapData>(fmd) {
					@Override
					public HashMapData get(
							HashMapData hashmapdata) {
						LivingBeing lb = hashmapdata
								.getLivingBeing();
						if (lb != null) {
							if (isHuman(lb)) {
								return null;
							}

							return hashmapdata;
						}

						return super.get(hashmapdata);
					}

				}, 8);
		if(gotten == null){
			return;
		}
		LivingBeing person = RandomPersonCreation
				.addRandomPerson(mainPerson.getParent(),
						VConstants.human, VConstants.human);
		person.put(VConstants.population, new PBase());
		person.getAlterHolder().put(VConstants.weapon, new Item("spear"));
		final LivingBeing gottenPerson = gotten.getLivingBeing();
		OobList olist = new OobList(VConstants.water,new Move(gotten, "fish",
				VConstants.food), new SimpleOObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {

				Double pinc = getGrowth(person);
				person.getPopulation().put(
						SConstants.huntingsuccess, true);
//								if (gotten == null
//										|| !Point.nextTo(person, gotten)) {
//									person.getPopulation().put(
//											SConstants.huntingsuccess, false);
//									int food = 0;
//									if (PBase.getDefaultBoolean(this,
//											SConstants.gather, true)) {
//										food = 5;
//										MoveRandomHashMapData moveRandom = new MoveRandomHashMapData(
//												SConstants.gather);
//										moveRandom.put(VConstants.overlay,
//												"happy");
//										addToList(person, moveRandom);
//										return null;
//									}
//
//									int pop = person.getType(
//											VConstants.population).getInt(
//											VConstants.size);
//									FoodRule.getFood(food, person
//											.getType(VConstants.population),
//											pinc);
//									return null;
//								}
				if(gotten.getLivingBeing() == null){
					gottenPerson.put(VConstants.visualdamage, "damagesword");
					
					return null;
				} else {
					gottenPerson.put(VConstants.visualdamage, "damagebow");
					
				}
				
				PBase popg = gotten.getLivingBeing().getType(
						VConstants.population);
				Double popsize = PBase.getDouble(popg,
						VConstants.size);
//								popsize += TechnologyRule.getDefaultInt(person,
//										SConstants.hunting, 0);
				popsize = FoodRule.getFood(popsize,
						person.getType(VConstants.population),
						pinc);
				popg.put(VConstants.size, popsize);
				popg.put(vcon, object)
				return null;
			}

		}

		, new Move(mainPerson,
				"movebackh"), new RemovePerson());
OObject.setCurrent(person, olist);
	}

	private List<LivingBeing> onlyHumans(List<LivingBeing> people) {
		List<LivingBeing> l = new ArrayList<LivingBeing>();
		for (LivingBeing lb : people) {
			if (PeopleRule.isHuman(lb)) {
				l.add(lb);
			}
		}
		return l;
	}

	public static LivingBeing addPerson() {
		LivingBeing lb = RandomPersonCreation.createPerson("human female");
		mainPeople.add(lb);
		lb.getType(VConstants.population).put(VConstants.size, (double) 10);
		lb.getAlterHolder().put(VConstants.weapon, new Item("spear"));
		lb.getAlterHolder().put(VConstants.body, new Item("animal skin"));

		OObject.setCurrent(lb, new SimpleOObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				MoveRandomHashMapData moveRandom = new MoveRandomHashMapData(
						SConstants.gather);
				moveRandom.put(VConstants.overlay, "happy");
				OObject.addToList(person, moveRandom);
				return new Returnable(true);
			}
		});
		
		return lb;
	}

	public static boolean isHuman(LivingBeing lb) {
		return VConstants.human.equals(lb.getTeam());
	}

	@Override
	public PBase clone() {
		return new PeopleRule().copyProperties(this);
	}

	public static PBase getPopulation(LivingBeing lb) {
		return lb.getPBase(VConstants.population);
	}

	public static double getGrowth(LivingBeing person) {
		String name = person.getType();
		int maxsize = TechnologyRule.getDefaultInt(VConstants.person, name,
				VConstants.maxsize, 50);
		double growth = TechnologyRule.getDefaultInt(VConstants.person, name,
				VConstants.growth, 4);
		double size = PBase.getDouble(person.getPopulation(), VConstants.size);
		double inefficency = size / maxsize;
		growth = growth - inefficency * growth;
		if (growth < 1) {
			growth = 1;
		}
		return growth;

	}
}
