package gwt.client.game.vparams.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.GameUtil;
import gwt.client.game.vparams.GoTo;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class RandomSceneCreation extends VParams {
	public RandomSceneCreation() {
		// TODO Auto-generated constructor stub
	}
	public RandomSceneCreation(List list, List list2) {
		put(VConstants.map,new PBase());
		put(VConstants.positionlist,list);
		put(VConstants.namelist,list2);
	}
	
	public void add(String mapname,VParams vp){
		getPBase(VConstants.map).getListCreate(mapname).add(vp);
	}
	@Override
	public void execute(Map<String, Object> map) {
		if(!containsKey(VConstants.list)){
			List<List<String>> sceneNameList = getListCreate(VConstants.namelist);
			
			List<String> scenes = new ArrayList<String>();
			for(Integer i :(List<Integer>) get(VConstants.positionlist)){
				List<String> sns = sceneNameList.get(i);
				String sn = sns.remove(EntryPoint.game.getRandom().nextInt(sns.size()));
				scenes.add(sn);
			}
			
			put(VConstants.position,0);
			put(VConstants.list,scenes);
		}
		int current = getInt( VConstants.position);
		
		List<String> scenes = getListCreate(VConstants.list);
		
		if(current >= scenes.size()){
			return;
		}
		FullMapData fmd = EntryPoint.game.getHtmlOut().currentFMD;
		List<VParams> scene = (List<VParams>) getPBase(VConstants.map).get(scenes.get(current));
		
		List<LivingBeing> plist = GameUtil.getPlayerTeam(fmd.people);
		
		for(VParams vp : scene){
			vp.execute(map);
		}
		GoTo.setInitialPos(fmd, plist);
		put(VConstants.position,current+1);
		
	}
	
	@Override
	public PBase clone() {

		return new RandomSceneCreation().copyProperties(this);
	}
}
