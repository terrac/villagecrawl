package gwt.client.statisticalciv.oobjects;

import java.util.Arrays;
import java.util.List;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.rules.CreateItem;
import gwt.client.item.Item;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;

public class GatheringTech extends OObject {

	List<Item> li = Arrays.asList(new Item("strawberry"));
	List<Item> lNut = Arrays.asList(new Item("acorn"));
	/**
	 *high Random chance of creating a food item to go to
	 *
	 *move to the food item and get it
	 *
	 * once stone tools are researched add nuts for an extra chance
	 */
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		Item item =null;
		if(VConstants.getRandom().nextDouble() > .60){
			item=(Item) VConstants.getRandomFromList(li);
			
		}else if(VConstants.getRandom().nextDouble() > .20){
			item = (Item) VConstants.getRandomFromList(li);
			
		}
		if(item == null){
			return null;
		}
		FullMapData.addRandomSpot(fullMapData,item);
		
		return null;
	}

	@Override
	public OObject clone() {
		return new GatheringTech().copyProperties(this);
	}
}
