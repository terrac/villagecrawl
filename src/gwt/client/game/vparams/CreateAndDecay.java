package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

import java.util.List;
import java.util.Map;

public class CreateAndDecay extends VParams {
	public CreateAndDecay() {

	}

	

	/**
	 * 3 have them both just work on one object at a time (objects spawn and
	 * decay and take a long time to work) a make the putting of that item down
	 * add the item to the runturn (not a specific person) c spawn item and add
	 * to runturn for pop on decay d create some black spots to indicate decay
	 * at 50% e the pop method pops at 50 and sets the decay and then readds to
	 * remove
	 */
	
	public void execute(Map<String, Object> map) {
		for (FullMapData fmd : EntryPoint.game.getMapArea().getMap()) {
			for (HashMapData hmd : fmd) {
				if (hmd.getItems() != null) {
					hmd.increment(VConstants.decay);
					if (hmd.getInt(VConstants.decay) > 100) {
						hmd.getItems().clear();
						hmd.put(VConstants.decay, null);
					} else if(hmd.getInt(VConstants.decay) > 50){
						//put onto the item an overlay that denotes decay
					}
					continue;
				}
				MapData gate = hmd.getMapData(VConstants.gate);
				if(gate == null){
					continue;
				}
				List<String> list = EntryPoint.game.getPBase(
						VConstants.resource).getList(gate.getValue());
				
				if (list != null) {
					
					if (VConstants.getRandom().nextDouble() > .001) {
						continue;
					}
					String itemname = VConstants.getRandomFromList(list);
					Item item = EntryPoint.game.getItemMap().get(itemname);
					if(item ==null){
						if(fmd.people.size() > 4){
							continue;
						}
						LivingBeing lb=RandomPersonCreation.createPerson(itemname);
						hmd.getParent().getNearestEmpty(hmd).put(lb);
						lb.put(VConstants.economy, null);
					} else {
						hmd.getItemsCreate().put(item.clone());	
					}
					
				}

			}
		}
	}

	@Override
	public PBase clone() {

		return new CreateAndDecay().copyProperties(this);
	}

}
