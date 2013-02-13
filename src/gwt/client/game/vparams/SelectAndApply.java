package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class SelectAndApply extends VParams {

	public SelectAndApply() {
		// TODO Auto-generated constructor stub
	}
	
	public SelectAndApply(VParams... vpl) {
		put(VConstants.list,vpl);
	}

	@Override
	public void execute(Map<String, Object> map) {
		VParams vp=(VParams)VConstants.getRandomFromList(getList(VConstants.list));
		PBase pBase = getPBase(VConstants.resource);
		if(pBase != null){
			for(Entry<String,Object> ent : pBase.getObjMap().entrySet()){
				vp.put(ent.getKey(), ent.getValue());
			}
		}
		
		vp.execute(map);
		
	}
	
	@Override
	public PBase clone() {
		return new SelectAndApply().copyDeepProperties(this);
	}

}
