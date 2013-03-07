package gwt.client.statisticalciv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.client.statisticalciv.generator.nomadic.FishingGenerator;
import gwt.client.statisticalciv.generator.nomadic.PopulationGenerator;
import gwt.client.statisticalciv.generator.nomadic.TribalClothingGenerator;
import gwt.client.statisticalciv.generator.nomadic.TribalConflictGenerator;
import gwt.shared.StatisticalCivMap;
import gwt.shared.datamodel.VParams;

public class CreateInternal extends VParams {

	public CreateInternal() {
	}
	
	Map<String,List<VParams>> genMap = new HashMap();
	{
		genMap.put("tribal", Arrays.asList(new VParams[]{ new PopulationGenerator(),new FishingGenerator(),new TribalClothingGenerator(),new TribalConflictGenerator()}));
	}
	FullMapData overallfmd;
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		if(!Window.confirm(h.toString())){
			return;
		}
		map.put(VConstants.main, h);
		
		if(overallfmd != null){
			return;
		}
		//the initial fmd is hidden and the rules pause
		//or slow down
		FullMapData fmd;
		overallfmd = EntryPoint.game.getHtmlOut().currentFMD;
		EntryPoint.game.getMapArea().getMap().putiMD(0, 0, new FullMapData());
		EntryPoint.game.getHtmlOut().clearFMDs();
		EntryPoint.game.getHtmlOut().currentFMD=fmd = EntryPoint.game.getMapArea().getMap().getData(0, 0);
		RunRules.pause = true;
		
		StatisticalCivMap.getMap2().execute(AttachUtil.createMap(fmd, this));
		map.put(VConstants.fullmapdata, fmd);
		for(VParams vp :genMap.get("tribal")){
			vp.execute(map);
		}
		EntryPoint.game.pause = false;
		//create the previous trade stuff on the city
		// the trades of goods appear
		// technology choices
		
		
		// create nomads on nomadic spot
		// basically just people walking to a new spot
		// and it translates that back up and closes the map
		
		// create fight on fight spot
		
		//the total number of people is directly related to the population
		//the number of animals is related to the population
	}


	@Override
	public PBase clone() {
		return new CreateInternal().copyProperties(this);
	}
}
