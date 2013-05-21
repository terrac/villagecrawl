package gwt.client.statisticalciv;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class FoodRule extends VParams{
/**
 * 1 landrule that fills the land with food 
  a every square gets some food (dependent on tile+ little random)
  b if food is above X amount then an image is shown for it
2(also creates migrating herds), 
  a randomly pick spots to place cows
  b those cows search 1 extra tile for food and pick the most 90% of the time
  c

 */
	

	public void createCows(final FullMapData fmd){
		int count = TechnologyRule.getDefaultInt(SConstants.cows,5);
		
		for(HashMapData hmd :fmd){
			if(count > 0){
				LivingBeing cow = addCow();
				FullMapData.addRandomSpot(fmd, cow);

				count--;
			}
		}
	}
	
	public void createFish(final FullMapData fmd){
		int count = TechnologyRule.getDefaultInt(SConstants.cows,5);
		
		for(HashMapData hmd :fmd){
			MapData md=hmd.getMapData(VConstants.under);
			if(md!= null&&md.containsKey("fish")){
				SimpleMD fish = new SimpleMD(VConstants.obstacle, "goldfish");
				fish.put(VConstants.population, new PBase(VConstants.size,10.0));
				hmd.put(fish);
			}

		}
	}

public static LivingBeing addCow() {
	LivingBeing cow=RandomPersonCreation.createPerson("sheep");
	cow.getType(VConstants.population).put(VConstants.size,(double) 10);
	
	OObject.setCurrent(cow, new OObject() {
		double largest = 0;
		HashMapData largestH;
		
		@Override
		public Returnable execute(FullMapData fullMapData, LivingBeing person) {
			largest = 0;
			HashMapData toMove=fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
				@Override
				public HashMapData get(HashMapData hashmapdata) {
					double food= PBase.getDouble(hashmapdata,"wheat");
					if(food > largest){
						largest = food;
						largestH = hashmapdata;
					}
					
					return super.get(hashmapdata);
				}
				
			}, 2);
			if(largestH == null){
				return new Returnable(true);
			}
			addToList(person, new Move(largestH, "foodrule"));
			HashMapData hashmapdata = largestH;
			double food= PBase.getDouble(hashmapdata,"wheat");
			PBase pop = person.getType(VConstants.population);
			food = eatByPopulation(food, pop, PeopleRule.getGrowth(person));
			if(food < maxWheat * 3){
				hashmapdata.getItemsCreate().clear();
			}
			
			hashmapdata.put("wheat", food);
			return new Returnable(true);
		}
		@Override
		public OObject clone() {
			return this;
		}
	});
	return cow;
}




	public static double eatByPopulation(double food, PBase pop, double pinc) {
		double size = PBase.getDouble(pop, VConstants.size);
		
		food = -(pinc+size)+food;
		if(food < 0){
			if(size +food< 10){
				food = 1;
			}
			PBase.increment(pop, VConstants.size, food);
			
			food = 0;
		} else {
			PBase.increment(pop, VConstants.size, pinc);
			
		}
	return food;
}

	/**
	 * use growth to pick amount to grow
	 * 
	 * put a cap on growth
	 * 
	 * 
	 */
	boolean init;
	private static int maxWheat;
	@Override
	public void execute(Map<String, Object> map) {
		//for each hashmap in map, increment food by random amount
		FullMapData fmd = getFMD(map);
		if(!init){
			createCows(fmd);
			createFish(fmd);
			tileGrowth(fmd);
			maxWheat = TechnologyRule.getDefaultInt("maxWheat", 120);
			
			init = true;
		}
		
		for(HashMapData hmd :fmd){
			double growth = hmd.getInt("growth");
			if(growth == 0){
				modifyFish(hmd);
				continue;
			}
			double wheatAmount = PBase.getDouble(hmd,"wheat");
			double newAmnt = growth *3+(2 *VConstants.getRandom().nextDouble()) + PBase.getDouble(hmd,"wheat");
			
			//int maxWheat = PBase.getDefaultInt(EntryPoint.game.getTechnology().getPBase("maxwheat"),VConstants.size,35);
			if(wheatAmount > maxWheat * growth){
				continue;
			}
			if(wheatAmount > maxWheat * 3){
				hmd.getItemsCreate().put(new Item("wheat"));
			}
			if(newAmnt < 0){
				newAmnt = 0;
			}
			hmd.put("wheat", newAmnt);
			
		}
		
		
		

	}

	public void tileGrowth(FullMapData fmd) {
		PBase pb = new PBase("desert", 1);
		pb.put("rock", 2);
		pb.put("water", 0);
		pb.put("tree", 3);
		pb.put("swamp", 2);
		pb.put("plant", 2);
		pb.put("default", 4);
		pb.put("city", 0);
		pb.put("farm", 0);
		pb = PBase.getDefaultPBase(this, "growthmap", pb);
			for (final HashMapData hmd : fmd) {
				int growth = pb.getInt("default"); // get
				MapData under = hmd.getMapData(VConstants.under);
				if (under != null) {
					growth = pb.getInt(under.getValue());
				}
				MapData gate = hmd.getMapData(VConstants.gate);
				if (gate != null) {
					growth = pb.getInt(gate.getValue());
				}

				MapData obstacle = hmd.getMapData(VConstants.obstacle);
				if (obstacle != null) {
					growth = pb.getInt(obstacle.getValue());
				}

				hmd.put("growth", growth);

			}
	}
	public void modifyFish(HashMapData hmd) {
		MapData mapData = hmd.getMapData(VConstants.obstacle);
		if(mapData == null){
			return;
		}
		PBase pop = mapData.getPBase(VConstants.population);
		if(pop == null){
			return;
		}
		double size = PBase.getDouble(pop,VConstants.size);
		double pinc = size/10+7;
		eatByPopulation(pinc, pop, pinc);
		if(size >= TechnologyRule.getDefaultInt(SConstants.growthIteration, 1000)){
			pop.put(VConstants.size,(double)TechnologyRule.getDefaultInt(SConstants.growthIteration, 1000)-1);
			
		}
	}
	
	@Override
	public PBase clone() {
		return new FoodRule().copyProperties(this);
	}
}
