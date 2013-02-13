package gwt.client.game.vparams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.MapArea;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class SymbolicMapBuild extends VParams {

	public SymbolicMapBuild() {

	}

	public SymbolicMapBuild(VParams... params) {
		put(VConstants.list, Arrays.asList(params));
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		List<VParams> list = getListCreate(VConstants.list);
		
		MapArea mapArea = new MapArea(list.size(), 1);
		mapArea.game = EntryPoint.game;
		EntryPoint.game.setMapArea(mapArea);
		mapArea.getMap().init();
		boolean first = true;
		int a = 0;
		for(FullMapData fmd : mapArea.getMap()){
			if(a == 0){
				EntryPoint.game.getHtmlOut().currentFMD = fmd;
				list.get(a).execute(map);
				
			} else {
				AttachUtil.attach(AttachUtil.personStartOnMap,list.get(a),fmd);
			}
			a++;
		}
	}
	
	@Override
	public PBase clone() {
		
		return new SymbolicMapBuild().copyProperties(this);
	}
}
