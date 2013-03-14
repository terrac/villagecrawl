package gwt.client.statisticalciv;

import java.util.Map;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
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
		int count = 5;
		for(HashMapData hmd :fmd){
			if(count > 0){
				LivingBeing cow=RandomPersonCreation.createPerson("cow");

				
				OObject.addToList(cow, new OObject() {
					
					@Override
					public Returnable execute(FullMapData fullMapData, LivingBeing person) {
						fullMapData.getNearby(person, new GetForNearby<HashMapData>(fmd) {
							@Override
							public HashMapData get(HashMapData hashmapdata) {
								int food= hashmapdata.getInt(VConstants.food);
								food -= -1;
								if(food < 0){
									food = 0;
								}
								if(food < 4){
									hashmapdata.getItems().clear();
								}
								
								hashmapdata.put(VConstants.food, food);
								return super.get(hashmapdata);
							}
							
						}, 2);
						return new Returnable(true);
					}
				});

				count--;
			}
		}
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		//for each hashmap in map, increment food by random amount
		FullMapData fmd = getFMD(map);
		for(HashMapData hmd :fmd){
			hmd.increment(VConstants.food);
			if(hmd.getInt(VConstants.food) > 10){
				hmd.put(new Item("grapes"));
			}
		}
		
		//if under is grass and this is the first turn then create 4 cows
		
		//those cows have a custom action that checks for food (grass and
		//goes towards the most 90% of the time
		
		super.execute(map);
	}
}
