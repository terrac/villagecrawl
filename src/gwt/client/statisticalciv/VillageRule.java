package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.oobjects.Build;
import gwt.client.game.oobjects.MoveStore;
import gwt.client.game.vparams.Count;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
import gwt.client.main.PTemplate;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
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
				OObject.addToList(lb, new SimpleOObject() {
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
		
		for(HashMapData h : fmd){
			MapData farm = h.getMapData(VConstants.gate);
			if(farm != null&&farm.getValue().equals("farm")&&VConstants.getRandom().nextDouble() < .05){
				//bandits are created 1% of the time
				addBandit(h);
			}
		}
		for(final HashMapData cityH : hl){
			PBase city = cityH.getPBase(VConstants.gate);
			//farm growth
			int farmcount = city.getInt("farmcount");
			if(farmcount < 3){
				farmcount++;
				city.put("farmcount", farmcount);
				HashMapData farm=fmd.getNearby(cityH, new GetForNearby<HashMapData>(fmd) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						if(hashmapdata.containsKey(VConstants.gate)){
							return null;
						}
						return hashmapdata;
					}
				}, 4);
				if(farm != null){
					farm.remove("wheat");
					farm.put("growth", 0);
					SimpleMD md = new SimpleMD(VConstants.gate, "farm");
					farm.put(md);
					List<MapData> pbl=cityFarmMap.get(cityH);
					if(pbl == null){
						pbl = new ArrayList();
						cityFarmMap.put(cityH, pbl);
					}
					pbl.add(md);
					
				}
			}
			
		
			
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
	}

	private void addBandit(HashMapData h) {
		
		LivingBeing lb=RandomPersonCreation.addRandomPerson(h, VConstants.human, VConstants.human);
		lb.getAlterHolder().put(VConstants.weapon, new Item("dagger"));
		OObject.addToList(lb, new SimpleOObject() {
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
	}

	@Override
	public PBase clone() {
		return new VillageRule().copyProperties(this);
	}

}
