package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
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
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.shared.datamodel.VParams;

public class VillageRule extends VParams {

	private List<LivingBeing> banditList = new ArrayList<LivingBeing>();
	public VillageRule() {
	}
	static List<HashMapData> farmList = new ArrayList<HashMapData>();
	static List<String> bodyList = new ArrayList<String>(Arrays.asList(new String[] {
			"leather armor", "black coat", "animal skin",
			"green breastplate", "dress", "robe", "rags" }));
	TimeRule tr = new TimeRule();
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);

		tr.execute(map);
		for(LivingBeing person : fmd.people.toArray(new LivingBeing[0])){		//.005
			if(person.getParent() == null){
				continue;
			}
			person.getStats().put(VConstants.health,(int) person.getPopulation().getDouble(VConstants.size));
			person.getStats().put(VConstants.maxhealth, person.getPopulation().getInt(SConstants.totalsize));

			if(PeopleRule.isHuman(person)&&VConstants.getRandom().nextDouble() < .005){
				doFarmer(fmd, person);
			}
			doLeaderConflict(person,fmd);
			
		}

		
		
		for(HashMapData hmd : farmList){			//.005
			if(VConstants.getRandom().nextDouble() < .005){
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
	
	private void doLeaderConflict(final LivingBeing person,FullMapData fmd) {
		if(person.getParent() == null){
			return;
		}
		fmd.getNearby(person, new GetForNearby<HashMapData>(fmd) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				LivingBeing lb = hashmapdata.getLivingBeing();
				if(lb != null&&!lb.equals(person)){
					if(VConstants.getRandom().nextDouble() > .8){
						return null;
					}
					String lbType = lb.getPopulation().getS(VConstants.type);
					String personType = person.getPopulation().getS(VConstants.type);
					if(VConstants.leader.equals(lbType)){
						//do damage to each other
						if(testType(VConstants.leader,personType)||testType(SConstants.bandit,personType)){
							PeopleRule.conflictDamage(person, lb, 1.0);
						}
							
					}
					if(SConstants.bandit.equals(lbType)){
						//do damage to each other
						if(PeopleRule.isHuman(person)){
							PeopleRule.conflictDamage(person, lb, 1.0);
						}
						
					}
				}
				return null;
			}
		}, 2);
	}

	public static boolean testType(String t1, String t2) {
		if(t1 == null || t2 == null){
			return false;
		}
		return t1.equals(t2);
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
		lb.getAlterHolder().put(VConstants.body, new Item(VConstants.getRandomFromList(bodyList)));
		lb.getPopulation().put(VConstants.type, SConstants.bandit);
		lb.getPopulation().put(VConstants.size, 10.0);
		OObject.setCurrent(lb, new SimpleOObject() {
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				//bandits randomly pick a nearby spot and try to raid that farm
				//bandits have a % chance of moving back to their initial farm after every raid
				//if they do they increment a raid counter which warrior creation uses
				final HashMapData home = getHome(person);
				
				
				HashMapData hmd=fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						if(home.equals(hashmapdata)){
							return null;
						}
						MapData gate = hashmapdata.getMapData(VConstants.gate);
						double chance = .40;
						if(hashmapdata.containsKey(VConstants.owned)){
							chance += .30;
						}
						if(gate != null && SConstants.farm.equals(gate.getValue())&&VConstants.getRandom().nextDouble() > chance){//
							return hashmapdata;
						}
						return null;
					}
				}, 5);
				
				if(hmd != null){
					OobList oobList = new OobList(new Move(hmd, "raid"),new Wait("happy", 7));
					addToList(person, oobList);
					//add to raid counter
					if(VConstants.getRandom().nextDouble() < .05){
						//death from raiding
						oobList.addNextAction(new RemovePerson());
						
					}
					
					//turn into leader
					if(VConstants.getRandom().nextDouble() < .3){
						PBase.increment(person.getPopulation(),VConstants.size,20.0);
						person.getPopulation().put(SConstants.totalsize,100);
						doLeader(person);
					}
					if(VConstants.getRandom().nextDouble() < .30){
						PBase.increment(person.getPopulation(),VConstants.size,5.0);
						oobList.addNextAction(new Move(home, "returnhome"));
						oobList.addNextAction(new Wait("happy", 7));
					}
				}
				
				return new Returnable(true);
			}
		});

	}

	protected void setHome(LivingBeing person, HashMapData parent) {
		person.getPopulation().put(VConstants.home, parent.getPosition());
	}

	protected HashMapData getHome(LivingBeing person) {
		Point p=(Point) person.getPopulation().get(VConstants.home);
		if(p == null){
			setHome(person, person.getParent());
			
			p=(Point) person.getPopulation().get(VConstants.home);
		}
		return person.getParent().getParent().getData(p);
	}

	protected void doLeader(final LivingBeing person) {
		//give a sword
		person.getAlterHolder().remove(VConstants.weapon);
		person.getAlterHolder().put(VConstants.weapon, new Item("axe"));
		
		person.getPopulation().put(VConstants.type, VConstants.leader);
		person.getTemplate().clear();
		//clear oobjects
		HashMapData home = getHome(person);
		person.put(VConstants.name, person.getId());
		final List<HashMapData> ownedList = new ArrayList<HashMapData>();
		person.getParent().getParent().getNearby(home, new GetForNearby<HashMapData>(home.getParent()) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				ownedList.add(hashmapdata);
				return null;
			}
		}, 5);
		
		OObject moveLeader = new SimpleOObject() {
			int count;
			@Override
			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
				HashMapData h=ownedList.get(count);
				//eventually make more complicated
				h.put(VConstants.owned,person.getName());
				
				count++;
				if(count >= ownedList.size()){
					count = 0;
				}
				addToList(person, new OobList(new Move(h,"")));
				//take some tribute
				PBase.increment(person.getPopulation(),VConstants.size,10.0);
				return new Returnable(true);
			}
		};
		//set current object
		OObject.setCurrent(person, moveLeader);
		
	}

	public static void doFarmer(FullMapData fmd, final LivingBeing mainPerson) {
		HashMapData hmd=fmd.getNearKeyValue(VConstants.gate, SConstants.farm, mainPerson.getPosition(), 10);
		if(hmd == null){
			hmd = mainPerson.getParent();
		}
		Point randomFertileSpot = getRandomFertileSpot(fmd);
		final HashMapData farm=fmd.getNearby(randomFertileSpot, new GetForNearby<HashMapData>(fmd) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.isBlock()) return null;
				
				if(hashmapdata.containsKey(VConstants.gate)){
					MapData pfarm = hashmapdata.getMapData(VConstants.gate);
					if(pfarm.getValue().equals(SConstants.farm)){
						PBase pop = pfarm.getType(VConstants.population);
						if(pop.getDouble(VConstants.size)< 1){//50
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
	
	static List<Point> fertileSpots;
	private static Point getRandomFertileSpot(FullMapData fmd) {
		if(fertileSpots == null){
			fertileSpots = new ArrayList<Point>();
			for(HashMapData hmd : fmd){
				MapData obstacle = hmd.getMapData(VConstants.under);
				if(obstacle != null&&obstacle.containsKey("fish")){
					fertileSpots.add(hmd.getPosition());
				}
			}
		}
		return VConstants.getRandomFromList(fertileSpots);
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
