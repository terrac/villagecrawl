package gwt.client.game;

import java.util.Map;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

public class IfKey extends VParams {

	public IfKey() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmd=(HashMapData) map.get(AttachUtil.OBJECT);
		if(hmd.containsKey(getS(VConstants.key))){
			((VParams) get(VConstants.param)).execute(map);
		}
		
	}
	public IfKey(String key, VParams param) {
		super();
		put(VConstants.key,key);
		put(VConstants.param,param);
		
	}
	@Override
	public PBase clone() {
		
		return new IfKey().copyProperties(this);
	}
}
