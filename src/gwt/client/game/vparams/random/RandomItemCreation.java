package gwt.client.game.vparams.random;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.item.Item;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class RandomItemCreation extends VParams{

	public RandomItemCreation() {
		
	}
	public RandomItemCreation(int range, String ... types) {
		put(VConstants.range,range);
		put(VConstants.type,types);
	}
	@Override
	public void execute(Map<String, Object> map) {
		//take level score		
		//get current fmd, get the score off of it
		
		//take variables
		// range of - to + of damage modifiers
		
		// describe shape (default is rareness increases at a power of 2  so the top are incredibly rare
		
		// take list of extra properties to add (brandings for weapons, specific extra abilities like haste or strength, additional
		
		//
		int size = getInt(VConstants.size);
		if( size == 0){
			size = 1;
		}
		for(LivingBeing lb :EntryPoint.game.getHtmlOut().currentFMD.people){
			for(int a=0; a < size; a++){
				Item randomize = randomize(null);
				//randomize.setAmount(2);
				lb.addItems(randomize);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gwt.client.game.vparams.random.RandomCreation#randomize(gwt.client.map.FullMapData)
	 */
//	public Item randomize(String type){
//
//		
//		int range = getInt(VConstants.range);
//		FullMapData fmd=EntryPoint.game.getHtmlOut().currentFMD;
//		Integer level=(Integer) fmd.get(VConstants.level);		
//		Integer toplevel =(Integer) get(VConstants.level);
//		
//		if(level != null&&toplevel != null){
//			range = range * level/toplevel;
//		}
//		Game game = EntryPoint.game;
//		int plusmodifier=game.getRandom().nextInt(range);
//		
//		List<String> types = getList(VConstants.type);
//		if(type == null){
//			type =  types.get(game.getRandom().nextInt(types.size()));			
//		}
//		Item item=game.getItemMap().get(type).clone();
//		int i=item.getInt(VConstants.armor);
//		if(i != 0){
//			item.put(VConstants.armor, i + plusmodifier);
//		}
//		
//		i=item.getInt(VConstants.damageadded);
//		if(i != 0){
//			item.put(VConstants.damageadded, i + plusmodifier);
//		}
//		
//		i=item.getInt(VConstants.value);
//		if(i != 0){
//			item.put(VConstants.value, i+i * plusmodifier);
//		}
//		item.put(VConstants.description, type+" +"+plusmodifier+" gold:"+(i+i * plusmodifier));
//		//do bonuses specific to certain types of items
//		
//		
//		return item;
//	}
	
	public Item randomize(String type){

		
		int range = getInt(VConstants.range);
		FullMapData fmd=EntryPoint.game.getHtmlOut().currentFMD;
		Integer level=(Integer) fmd.get(VConstants.level);		
		Integer toplevel =(Integer) get(VConstants.level);
		
		if(level != null&&toplevel != null){
			range = range * level/toplevel;
		}
		Game game = EntryPoint.game;
		int plusmodifier=game.getRandom().nextInt(range);
		
		List<String> types = getList(type);
		
		type = VConstants.getRandomFromList(types);
		Item item=game.getItemMap().get(type).clone();
		int i=item.getInt(VConstants.armor);
		if(i != 0){
			item.put(VConstants.armor, i + plusmodifier);
		}
		
		i=item.getInt(VConstants.damageadded);
		if(i != 0){
			item.put(VConstants.damageadded, i + plusmodifier);
		}
		
		i=item.getInt(VConstants.value);
		if(i != 0){
			item.put(VConstants.value, i+i * plusmodifier);
		}
		item.put(VConstants.description, type+" +"+plusmodifier+" gold:"+(i+i * plusmodifier));
		//do bonuses specific to certain types of items
		
		
		return item;
	}
	@Override
	public PBase clone() {
		
		return new RandomItemCreation().copyProperties(this);
	}
}
