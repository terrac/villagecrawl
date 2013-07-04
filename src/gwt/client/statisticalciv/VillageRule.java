package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
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
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.shared.datamodel.VParams;

public class VillageRule extends VParams {

	private List<LivingBeing> banditList = new ArrayList<LivingBeing>();
	public VillageRule() {
	}
	static List<HashMapData> farmList = new ArrayList<HashMapData>();
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);

		for(LivingBeing person : fmd.people.toArray(new LivingBeing[0])){		//.005
			if(PeopleRule.isHuman(person)&&VConstants.getRandom().nextDouble() < .1){
				doFarmer(fmd, person);
			}
		}

		
		
		for(HashMapData hmd : farmList){			//.005
			if(VConstants.getRandom().nextDouble() < .04){
				doBandit(fmd, hmd);
			}
		}
		// for all the farms
		// they all start out having themselves as their own rulers
		// they generate their own population and their own raids
		// merchants, shepherds, fisherman
		
		//% chance of creating a new population
		//
		
		// a technology gets introduced where a leader population 
		// is created 
		
		// the leader picks a farm and creates a city population
		// specialists are then organized by that leader when
		// they move
		
		// toughs turn into fighters that seek out all
		// toughs and quell them instead of just opposing farms
		// (ie they don't fight each other)
		
		// on bronze leaders can create miners to mine tin in 
		// distant locations
		
		
		
	
	}
	
	private void doBandit(FullMapData fmd, HashMapData hmd) {
		//could add a vparam onto ondeath for bandit to change this
		for(LivingBeing lb :banditList ){
			if(lb.getParent() == null){
				banditList.remove(lb);
				break;
			}
		}
		if(banditList.size() > 5){
			return;
		}
		LivingBeing lb=RandomPersonCreation.addRandomPerson(hmd, VConstants.human, VConstants.human);
		banditList.add(lb);
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
						if(home.equals(hashmapdata)){
							return null;
						}
						MapData gate = hashmapdata.getMapData(VConstants.gate);
						if(gate != null && SConstants.farm.equals(gate.getValue())&&VConstants.getRandom().nextDouble() > .40){//
							return hashmapdata;
						}
						return null;
					}
				}, 5);
				
				if(hmd != null){
					OobList oobList = new OobList(new Move(hmd, "raid"),new Wait("happy", 7));
					addToList(person, oobList);
					//add to raid counter
					if(VConstants.getRandom().nextDouble() < .5){
						//death from raiding
						oobList.addNextAction(new RemovePerson());
						
					}
					
					//turn into leader
					if(VConstants.getRandom().nextDouble() < .5){
						doLeader(person);
					}
					if(VConstants.getRandom().nextDouble() < .30){
						oobList.addNextAction(new Move(home, "returnhome"));
						oobList.addNextAction(new Wait("happy", 7));
					}
				}
				
				return new Returnable(true);
			}
		});

	}

	protected void doLeader(LivingBeing person) {
		//give a sword
		
		//clear oobjects
		
		//set current object
		
		//visit every farm in order in a certain radius from home
		//take a little bit of tribute
		//fight or convert bandits along the way (convert adds to population,fight reduces both, either way other bandit pops dissapear)
		//leaders also fight any other leaders (the losing leader loses that farm and potentially dies)
		//bandits still spawn and raid, but they have a % chance of avoiding leader owned tiles
		
	}

	public static void doFarmer(FullMapData fmd, final LivingBeing mainPerson) {
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
		lb.getType(VConstants.population).put(VConstants.size, 5);
		lb.getAlterHolder().put(VConstants.weapon, new Item("scythe"));
		OObject.setCurrent(lb, new Move((HashMapData) farm,"createfarm"));
		OObject.addToList(lb, new SimpleOObject() {
			
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				MapData mdFarm = farm.getMapData(VConstants.gate);
				if(mdFarm != null){
					PBase.increment(mdFarm.getType(VConstants.population), VConstants.size, 3.0);
					person.death();
					return null;
				}
				farmList.add(farm);
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
				farmList.add(farm);
				person.death();
				return null;
			}
		});
	}
	
	public boolean countLimit(String name,int limit){
		int count = getInt(name);
		if(limit < count){
			return true;
		}
		put(name,count+1);
		return false;
	}
	public void countDecrement(String name){
		decrement(this, name, 1);
	}
	@Override
	public PBase clone() {
		return new VillageRule().copyProperties(this);
	}

	
}
