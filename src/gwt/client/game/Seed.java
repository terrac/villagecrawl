package gwt.client.game;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class Seed extends MapData implements Pop {

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return VConstants.gate;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return "seed";
	}
	@Override
	public void pop() {
		HashMapData hmd=(HashMapData) getParent();
		if(hmd== null){
			return;
		}
		
		hmd.remove(getKey());
		if(VConstants.getRandom().nextDouble() < .2){
			return;
		}
		hmd.getItemsCreate().put(EntryPoint.game.getItemMap().get("apricot").clone());
	}

}
