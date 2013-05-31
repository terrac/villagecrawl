package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.oobjects.Build;
import gwt.client.game.oobjects.MoveStore;
import gwt.client.game.vparams.Count;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Kill;
import gwt.client.main.Move;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VillageRule extends VParams {

	public VillageRule() {
	}
	
	public VillageRule(Object... vp) {
		super(vp);
	}
	static List<HashMapData> hl= new ArrayList<HashMapData>();
	Map<MapData,List<MapData>> cityFarmMap = new HashMap<MapData, List<MapData>>();
	int taxcount = 0;
	boolean init;
	public void initialConversion(FullMapData fmd) {
		if(init){
			return;
		}
		init=true;
		

		for(LivingBeing lb :fmd.people){
			
			//the oobject takes the current oob and holds it			
			if(PeopleRule.isHuman(lb)){
				final OObject o = lb.getTemplate().getCurrent();
				lb.getTemplate().clear();
				OObject.setCurrent(lb, new SimpleOObject() {
					@Override
					public Returnable execute(FullMapData fullMapData, LivingBeing person) {
						//over time less chance of this finding a city
						if(VConstants.getRandom().nextDouble() > .20){
							o.execute(fullMapData, person);
							return new Returnable(true);
						}
						HashMapData hmd = fullMapData.getNearKeyValue(VConstants.obstacle, VConstants.water, person, 2);
						if(hmd == null){
							return new Returnable(true);
						}
						HashMapData hmdcity = fullMapData.getNearKeyValue(VConstants.gate, SConstants.city, person, 4);
						if(hmdcity != null){
							return new Returnable(true);
						}
						person.getParent().put(new SimpleMD(VConstants.gate, SConstants.city));
						person.getParent().remove("wheat");
						person.getParent().put("growth", 0);
						person.getParent().getMapData(VConstants.gate).put(VConstants.population, person.getPopulation());
						hl.add(person.getParent());
						person.death();
						
						return null;
					}
				});
			}
			//it checks nearby and sees if there is a suitable spot
			
			//if there is a suitable spot then move to that spot and create a town and remove
			//the person
			
		}
		
	}

	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);
		initialConversion(fmd);
		
		for(final HashMapData cityH : hl){
			PBase city = cityH.getPBase(VConstants.gate);
			Point cityloc = cityH.getPosition();
			Items it = getItems(city);
			PBase typeCount=updateTypeCount(city, it);
			
			//take the city population and increment it
			
			//if city pop is greater than a certain amount split a population out
			//and determine what it does next 
			PBase citypop =city.getPBase(VConstants.population);
			List<PBase> popSpecial = city.getListCreate(VConstants.list);
			List<PBase> farmPops = city.getListCreate(SConstants.farm+VConstants.list);
			PBase.increment(citypop, VConstants.size, .3);
			double citypopsize = citypop.getDouble(VConstants.size);
			if(citypopsize >10){
				PBase newPop = new PBase(VConstants.size,4.0);
				popSpecial.add(newPop);
				PBase.decrement(citypop, VConstants.size, 4.0);
			}
			
			for(PBase pop:popSpecial){
				String poptype = pop.getS(VConstants.type);
				Point home = (Point) pop.get(SConstants.home);
				//if population has a type it starts its actions, or creates items
				
					//the rest allocates new special types
				
					//if farmer, has farm, and has items, trade items and move back to farm
					if(SConstants.farm.equals(poptype)){
						if(pop.getInt(VConstants.food) > 50){
							//if at farm, move to city (put this in the farm list)
							
							//sell food incrementally
							
							
						} else {
							//if at city move back to farm
						}
					}
					//if guard then check raid count, if raid count is high then visit farms, fight bandits
					if(SConstants.guard.equals(poptype)){
						Item i=it.getItem(SConstants.raid);
						if(i != null&&i.getAmount() > 0){
							i.subtract(1);
							//move to each farm once and initiate a chance of fighting bandits
						}
					}
						
					//if local  merchant take highest item and some debt, trade distantly
					if(SConstants.merchant.equals(poptype)){
						//for all actions do them incrementally
						if(cityloc.equals(home)){
							//if has currency and debt, sell currency and remove debt
							
							//get highest type
							//put debt item on city
							//put debt item on merchant
							//put items on merchant
							//send merchant to foriegn city	
						} else {
							//if foriegn merchant sell highest item 
							//start moving back to home
						}
					}
					//if merchant and debt give item back to city equivalent to debt plus extra
					//if merchant has money count that against the debt first
					
					//if specialist then build associated items (do the most valuable with items available
					// pay rent, buy food (in addition to city pop there are the city goods which
					//farmers and specialists buy and sell to, guards get their pay from
					//and merchants take debt from
				
					//if bandit sell stolen goods, get money, spend
				
					//if beggar take money from city
					
				//if low on food items, 
				//city keeps a running tally of types, pull that type
				if(typeCount.getInt(VConstants.food) < citypopsize&&waitBeforeAnotherDecision(city,SConstants.farm,4)){
					addFarmer(fmd, cityH, pop,farmPops);
					popSpecial.remove(pop);
					System.out.println(popSpecial);
					System.out.println(pop);
					break;
				}
				//thats just a specific item to check
				//if high raid item count (delivering lots of reports about raids)
				Item raidcount = it.getItem(SConstants.raid);
				if(raidcount != null&&raidcount.getAmount()> 5&&waitBeforeAnotherDecision(city,SConstants.raid,50)){
					//turn to guards
					pop.put(VConstants.type, SConstants.guard);
					
					
				}
				// if highest amount type (also set) is 50 greater than lowest amount type
				//if high internal eresources
				
				if(checkMerchantItems(it)&&hl.size() > 1){
					pop.put(VConstants.type, SConstants.merchant);
					
					//turn to merchants
				}
				//if low on items
				if(false){
					//turn to various other specialists
				}
				if(waitBeforeAnotherDecision(citypop, SConstants.disaffected, 50)){
					PBase.increment(pop, SConstants.disaffected, 1);
				}
				if(pop.getInt(SConstants.disaffected) > 5){
					pop.put(VConstants.type, SConstants.beggar);
					
				}
				//otherwise add a disaffected counter
				//if enough disaffected the beggar or bandit
			}	
			
			for(PBase pop:farmPops){
				String poptype = pop.getS(VConstants.type);
				Point home = (Point) pop.get(SConstants.home);
				
				//create food
				//if food is high enough create action to move to city
				//
				if(SConstants.farm.equals(poptype)){
					PBase.increment(pop, VConstants.food, 1);
					if(pop.getInt(VConstants.food) > 50){
						//move to city
						PBase.decrement(pop, VConstants.food, 30);
						PBase.increment(city.getPBase(VConstants.population), VConstants.food, 30);
						LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
						lb.getAlterHolder().put(VConstants.weapon, new Item("scythe"));
						
						OObject.setCurrent(lb, new Move(fmd.getData(home),"movecity"));
						OObject.addToList(lb, new RemovePerson());
						
					}
				}
				if(VConstants.getRandom().nextDouble() < .01){
					addBandit(fmd.getData(home));
				}
				if(VConstants.getRandom().nextDouble() < .01){
					LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
					lb.getAlterHolder().put(VConstants.weapon, new Item("staff"));
					OObject.setCurrent(lb, new MoveClosest(VConstants.livingbeing,VConstants.cow));
					OObject.addToList(lb, new RemovePerson());
					
					break;
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			//run through population list
			//if population is disaffected it has a chance of becoming beggars,bandits, etc
			//run through farms
			//chance of becoming bandits, or moving to the city
			
			
			
			
			
			//tax men split off from the pop at regular intervals and collect resources
			
			//disasters hit cities and farms and this will rebalance things
			// the main disaster is flooding and that gives rise to progressivism
			// progressivism is basically the list of morality literature
			// it confers a benefit, but only rises in response to natural or manmade disaster
			// not including disease
			
			//farms take up every grass spot eventually though they can be lost due to poor farming/ drought/ floods/ etc (farms occasionally transfer populations in between themselves)
			
			//farms have a small % chance of creating a raider population.  Raiders steal and occasionally return to the original farm
			//they can grow over time and become independent, 
			
			//smallpox at some point is introduced and gets spread through trade
			//smallpox absolutely devistates everyone and fundamentalism arises
			//as the domninant social order (
			// list the laws of the bible
		}
		
//		//give the farm
//		for(HashMapData h : fmd){
//			MapData farm = h.getMapData(VConstants.gate);
//			if(farm != null&&farm.getValue().equals("farm")&&VConstants.getRandom().nextDouble() < .05){
//				//bandits are created 1% of the time
//				addBandit(h);
//			}
//		}
//		for(final HashMapData cityH : hl){
//			PBase city = cityH.getPBase(VConstants.gate);
//			//farm growth
//		
//			
//			
//		
//			
//			toBeDoneLater(fmd, cityH, city);
//		}
	}

	public void toBeDoneLater(FullMapData fmd, final HashMapData cityH,
			PBase city) {
		taxcount++;
		if(taxcount > 50){
			taxcount = 0;
			LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
			lb.getAlterHolder().put(VConstants.body, new Item("robe"));
			
			for(MapData md :cityFarmMap.get(cityH)){
				OObject.addToList(lb, new Move((HashMapData) md.getParent(),"taxes"));
				OObject.addToList(lb, new SimpleOObject() {
					
					@Override
					public Returnable execute(FullMapData fullMapData, LivingBeing person) {

						//tax man
							//create person
							//person creates a list of moves to make
							//person takes value equal to 10 coins
							
						return null;
					}
				});
				}
			lb.death();
		}
		int raidcount = city.getInt(SConstants.raid);
		if(raidcount > 0){
			raidcount = 0;
			//create warriors
			//take raid creation count.  Create a warrior for every 5 raids and set the raid counter
			//to 0
			//if enemy raid counter is 5 then create a warrior that leads all your warriors
			//to attack
			
			LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
			for(MapData md :cityFarmMap.get(cityH)){
				OObject.addToList(lb, new Move((HashMapData) md.getParent(),"taxes"));
				OObject.addToList(lb, new SimpleOObject() {
					
					@Override
					public Returnable execute(FullMapData fullMapData, LivingBeing person) {
						// set raid count to 0 on individual farms maybe
						// (raid count would 
							
						return null;
					}
				});
				}
			lb.death();
		}
		
		if(true){
			//create traders
			
			LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
			for(MapData md :cityFarmMap.keySet()){
				OObject.addToList(lb, new Move((HashMapData) md,"trading"));
				OObject.addToList(lb, new SimpleOObject() {
					
					@Override
					public Returnable execute(FullMapData fullMapData, LivingBeing person) {
						//if there is enough extra traders take the extra
						//and exchange it for different items at a different trade area
						
						return null;
					}
				});
			}
			OObject.addToList(lb, new Move(cityH,"backhome"));
			lb.death();

		}
		
		if(false){
			//create kedeshah 
			//they go to wandering nomadic populations and try to convert them
			//if successfull they get a big boost to farming/warriors
		
			LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
			for(LivingBeing p : fmd.people){
				//if is nomad
				//move to nomad
				//give a chance of conversion.  If successfull nomad goes to city
				//gives a boost and then dies
			}
			OObject.addToList(lb, new Move(cityH,"backhome"));
			lb.death();

		}
		
		//create sheperds
			//they travel out and have a icon to show sheep
			//they eat wheat like cows
			//occasionally they come back
		if(false){
			LivingBeing lb=FoodRule.addCow();
			final OObject o = lb.getTemplate().getCurrent();
			SimpleOObject shep = new SimpleOObject() {
				int count = 100;
				@Override
				public Returnable execute(FullMapData fullMapData, LivingBeing person) {
					if(count < 0){
						addToList(person, new Move(cityH, ""));
					}
					return o.execute(fullMapData, person);
					
				}
			};
			shep.put(VConstants.overlay, "/images/human.png");
			OObject.addToList(lb, shep);
		}
		if(false){
			//create disasters
			//  a disease
				//create a disease on a specific farm/shepherd
				//diseases are either active or passive
				//when active they are actively causing damage
				//also they spread when active
				//diseases open up a series of technologies that 
				//are religious and potentially prevent disease
		    //b flooding (rain)
				//hit some farms and the city
				//this opens up progressivism (which is counter to objectivism)
		    // c famine
				//sudden catastrophic losses of farms
				//can potentially destroy a city (ie turn back to nomadic)
				//helps encourage technologies around sustainable growth
			//d animal attacks  
				//create an animal that attacks a farm
			
		}
	}

	public void addFarmer(FullMapData fmd, final HashMapData cityH, PBase pop, final List<PBase> farmPops) {
		//turn to farmers, they pick a farm or create a new one
		pop.put(VConstants.type, SConstants.farm);
		final HashMapData farm=fmd.getNearby(cityH, new GetForNearby<HashMapData>(fmd) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.isBlock()) return null;
				
				if(hashmapdata.containsKey(VConstants.gate)){
					MapData pfarm = hashmapdata.getMapData(VConstants.gate);
					if(pfarm.getValue().equals(SConstants.farm)){
						PBase pop = pfarm.getPBase(VConstants.population);
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
		LivingBeing lb=RandomPersonCreation.addRandomPerson(cityH, VConstants.human, VConstants.human);
		lb.put(VConstants.population, pop);
		lb.getAlterHolder().put(VConstants.weapon, new Item("scythe"));
		OObject.setCurrent(lb, new Move((HashMapData) farm,"createfarm"));
		OObject.addToList(lb, new SimpleOObject() {
			
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				MapData mdFarm = farm.getMapData(VConstants.gate);
				if(mdFarm != null){
					PBase.increment(mdFarm.getPBase(VConstants.population), VConstants.size, person.getPopulation().getDouble(VConstants.size));
					person.death();
					return null;
				}
				farm.remove("wheat");
				farm.getItemsCreate().clear();
				farm.put("growth", 0);
				SimpleMD md = new SimpleMD(VConstants.gate, "farm");
				farm.put(md);
				person.getPopulation().put(VConstants.home, farm.getPosition());
				md.put(VConstants.population,person.getPopulation());
				farmPops.add(person.getPopulation());
				List<MapData> pbl=cityFarmMap.get(cityH);
				if(pbl == null){
					pbl = new ArrayList();
					cityFarmMap.put(cityH, pbl);
				}
				pbl.add(md);
				person.death();
				return null;
			}
		});
	}

	private boolean waitBeforeAnotherDecision(PBase city, String farm, int wait) {
		int turn = EntryPoint.game.getTurn();
		int waitturn = city.getInt("waitturn"+farm);
		if(turn > waitturn + wait){
			city.put("waitturn"+farm, turn);
			return true;
		}
		return false;
	}

	public boolean checkMerchantItems(Items it) {
		
		for(Item i :it.values()){
			
		}
		return false;
	}

	
	

	public PBase updateTypeCount(PBase city, Items it) {
		PBase typecount=city.getType("typecount");
		typecount.getObjMap().clear();
		for(Item i :it.values()){
			typecount.increment(typecount, i.getType(), i.getAmount());
		}
		return typecount;
	}

	public Items getItems(PBase city) {
		Items it=(Items) city.get(VConstants.items);
		if(it == null){
			it = new Items();
			city.put(VConstants.items, it);
		}
		return it;
	}

	private void addBandit(HashMapData h) {
		
		LivingBeing lb=RandomPersonCreation.addRandomPerson(h, VConstants.human, VConstants.human);
		lb.getAlterHolder().put(VConstants.weapon, new Item("dagger"));
		OObject.setCurrent(lb, new SimpleOObject() {
			HashMapData home;
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				//bandits randomly pick a nearby spot and try to raid that farm
				//bandits have a % chance of moving back to their initial farm after every raid
				//if they do they increment a raid counter which warrior creation uses
				if(home == null){
					home = person.getParent();
				}
				HashMapData hmd=fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						if(hashmapdata.containsValue("farm")&&VConstants.getRandom().nextDouble() > .40){
							return hashmapdata;
						}
						return super.get(hashmapdata);
					}
				}, 5);
				
				if(hmd != null){
					addToList(person, new Move(hmd, "raid"));
					//add to raid counter
					if(VConstants.getRandom().nextDouble() > .30){
						addToList(person, new Move(home, "returnhome"));
						addToList(person, new SimpleOObject() {
							
							@Override
							public Returnable execute(FullMapData fullMapData, LivingBeing person) {
								person.death();
								return null;
							}
						});
					}
				}
				
				return null;
			}
		});
		OObject.addToList(lb, new RemovePerson());
		
	}

	@Override
	public PBase clone() {
		return new VillageRule().copyProperties(this);
	}

}
