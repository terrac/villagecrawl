package gwt.client.game.vparams.random;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.SymbolicMapBuild;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class RandomOverMapCreator extends VParams implements RandomCreation {

	@Override
	public void execute(Map<String, Object> map) {

		// the randomeventcreator picks this event. It goes into all the types
		// of monsters and selects one (you could add teams if you wanted it to
		// do mulitple)
		// then it puts this event on the overmap as a empty tile (with a glowy
		// bit to signify interest), it also picks friendly (no type conflicts
		// like a paladin would have undead=hates, only things that hate the
		// undead or are netural would be allowed
		FullMapData fmd = EntryPoint.game.getHtmlOut().currentFMD;
		if(fmd.getInt(VConstants.xsize) == 0){
			fmd.put(VConstants.xsize, 10);
			fmd.put(VConstants.ysize, 10);
		}
		
		for (int a = 0; a < 10;a++) {
			MapData md=randomize(null);
			
			
			
			
			fmd.initIfNeeded();
			for (HashMapData hmd : fmd) {
				if (hmd.isBlock()) {
					continue;
				}
				if (VConstants.getRandom().nextInt(100) <= 3) {
					hmd.putAppropriate(md);
					break;
				}
			}
		}
	}
	
	@Override
	public MapData randomize(List<String> categories) {
		// pick an event from the random list
		List<VParams> events = getList(VConstants.event);

		VParams vp = VConstants.getRandomFromList(events);
		if(vp.getB(VConstants.unique)){
			events.remove(vp);
		}
		
		List<String> l = vp.getListCreate(VConstants.categories);
		String discreteCat = null;
		String genericCat = null;
		
			for (String cat : l) {
				if (VConstants.friendly.equals(cat)) {
					// see friendly, go through type and assoc classes to make sure
					// no conflicting types
					// the random events/places with the categories are run through
					// and placed on the map
					// a set of types is determined with the overmap that the places
					// cannot conflict with
					// (Ie for example if you have a land=forest, then anything that
					// has a different land cannot be used

					continue;
				}
				// see monster value, get from types everything with the category
				// monster, pick one
				PBase assocCat = EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.templatemap)
						.getPBase(VConstants.type);
				List<String> claa = CategoryUtil.getAssoc(cat, assocCat);
				genericCat = cat;
				discreteCat = VConstants.getRandomFromList(claa);
				// add categories

			}
		
		

		// some things are already discrete
		if (discreteCat == null) {
			discreteCat = l.get(0);
		}
		
		VParams tosend = (VParams) vp.clone();
		if(genericCat != null){
			tosend.put(VConstants.key, genericCat);
			tosend.put(VConstants.value, discreteCat);
		}
		
		// take final discrete category and apply the associated image

		String jsonname = "event"+discreteCat;
		if(map.containsKey(jsonname)){
			
			map.put(jsonname, map.get(jsonname)+1);
			jsonname += map.get(jsonname);
		} else {
			map.put(jsonname, 0);
		}
		ExitTile exittile = new ExitTile(
				jsonname,vp.getS(VConstants.name),tosend);
		exittile.put(VConstants.jsoncache, "event");
		
		
		return new SimpleMD(VConstants.gate, discreteCat,exittile, vp
				.getS(VConstants.image));
	}

	Map<String,Integer> map = new HashMap<String, Integer>();
	
	@Override
	public PBase clone() {
		
		return new RandomOverMapCreator().copyProperties(this);
	}
}
