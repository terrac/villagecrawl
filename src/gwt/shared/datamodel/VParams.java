package gwt.shared.datamodel;

import java.util.Arrays;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class VParams extends PBase{
	//holds a representation of how to edit this specific object, for example a string might have restrictions on the string (server and gwt side)
	//an int would parse to an int, etc
	
	public VParams() {
			
		}
	
	public VParams(Object... vp) {
		put(VConstants.vparams,Arrays.asList(vp));
	}
	
	public void init(){
		
	}
	public void execute(Map<String,Object> map){
		for(Object vp : getList(VConstants.vparams)){
			((VParams) AttachUtil.getMappedParam(vp)).execute(map);
		}
		
	}
	@Override
	public PBase clone() {
		
		return new VParams().copyProperties(this);
	}
	public LivingBeing getLivingBeing(Map<String,Object> map){
		if(map == null){
			return null;
		}
		Object object = map.get(AttachUtil.OBJECT);
		if(object == null){
			return null;
		}
		if( object instanceof LivingBeing){
			return (LivingBeing) object;
		}
		return ((HashMapData)object).getLivingBeing();
	}
	
	protected FullMapData getFMD(Map<String, Object> map) {
		return (FullMapData) map.get(AttachUtil.OBJECT);
	}

}
