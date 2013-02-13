package gwt.client.game.oobjects;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class BuildUnbuilt extends OObject {

	public BuildUnbuilt(PBase charmap, String map) {
		put(VConstants.pbase, charmap);
		put(VConstants.map, map);
	}
	public BuildUnbuilt(PBase charmap, String map,String unbuilt) {
		this(charmap, map);
		put(VConstants.unbuilt,unbuilt);
	}

	public BuildUnbuilt() {

	}
	{
		put(VConstants.overlay, "build");
	}
	int count = 0;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		count++;
		
		if(count > 5){
			count = 0;
			return null;
		}
		//find unbuilt gate
		if(fullMapData.get(VConstants.unbuilt) == null){
			HashMapData hmd=fullMapData.getNearKeyValue("gate.unbuilt", null, person, 30);
			if(hmd == null){
				return null;
			}
			//move to it
			addToList(person, new Move(hmd,""));
			return new Returnable(true);
			// the goto should take the character inside
		} else {
			
			// getnearest unbuilt
			// move to it
			final HashMapData hmd=fullMapData.getNearKeyValue("gate.unbuilt", null, person, 30);		
			if(hmd == null){
				PBase pBase = fullMapData.getPBase(VConstants.unbuilt).getPBase(VConstants.gate);
				if(pBase != null){
					
					pBase.remove("unbuilt");	
				}
				return null;
			}
			//move to it
			Move move = new Move(hmd,"");
			move.put(VConstants.portal, VConstants.not);
			addToList(person, move);
			MapData gate = (MapData) hmd.get(VConstants.gate);
			hmd.remove(VConstants.gate);
			final Object md = gate.get(VConstants.unbuilt);

			addToList(person, new Wait(3));
//			addToList(person, new OObject() {
//				
//				@Override
//				public Returnable execute(FullMapData fullMapData, LivingBeing person) {
					if(!VConstants.none.equals(md)&&!(md instanceof Boolean)){
						BuildMap.putData(hmd,md);
					}
//					return null;
//				}
//			});
			if(VConstants.getRandom().nextDouble() > .95){
				fullMapData.getNearestEmpty(hmd).put(RandomPersonCreation.createPerson("skeleton"));					
			}
			
			if(VConstants.getRandom().nextDouble() > .95){
				hmd.getItemsCreate().put(EntryPoint.game.getRandomItemCreation().randomize(VConstants.body));				
			}
			//gate.remove(VConstants.unbuilt);
			return new Returnable(true);
			// do putdata for that specific location
				
			//if getnearest has none then take earlier gate.  Make it characters property.  Remove unbuilt flag
			//person.getEconomy().registerProperty(hmd);
		}
		
		
		
		

	}

	@Override
	public OObject clone() {
		return new BuildUnbuilt().copyProperties(this);
	}

}
