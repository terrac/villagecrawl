package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class PutMap extends VParams{

	public PutMap() {
		// TODO Auto-generated constructor stub
	}
	
	public PutMap(String key, Object value) {
//		put(VConstants.xsymbolic,xs);
//		put(VConstants.ysymbolic,ys);
		setVals(null, null, key, value, null);
	}
	public PutMap(int xf, int yf, String key, Object value,String type) {
//		put(VConstants.xsymbolic,xs);
//		put(VConstants.ysymbolic,ys);
		setVals(xf, yf, key, value, type);
	}
	
	public PutMap(int xf, int yf, String key, Object value) {

		setVals(xf, yf, key, value, null);
	}
	public PutMap(String bm, int xf, int yf, String key, Object value,String type) {
		put(VConstants.bagmap,bm);
		setVals(xf, yf, key, value, type);
	}
	
	public void setSymbolic(int xs, int ys){
		put(VConstants.xsymbolic,xs);
		put(VConstants.ysymbolic,ys);
	}
	private void setVals(Integer xf, Integer yf, String key, Object value, String type) {
		put(VConstants.xfull, xf);
		put(VConstants.yfull,yf);
		put(VConstants.key, key);
		put(VConstants.value,value);
		put(VConstants.type,type);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		
		Object o = get(VConstants.value);
		if( o instanceof String){
			o = ((PBase)EntryPoint.game.getMap(getS(VConstants.type)).get(o)).clone();
		}
		String key = getS(VConstants.key);
		
		
				
		FullMapData fmd=EntryPoint.game.getHtmlOut().currentFMD;
		if(containsKey(VConstants.xsymbolic)){
			fmd=EntryPoint.game.getMapArea().getMap().getData(getInt(VConstants.xsymbolic),getInt(VConstants.ysymbolic));
			
		}
		String bm = getS(VConstants.bagmap);
		if(bm != null){
			BagMap bag=(BagMap) EntryPoint.game.getVParams().get(bm);
			bag.getBagMap().initIfNeeded();
			bag.getBagMap().setData(getInt(VConstants.xfull),getInt( VConstants.yfull),(MapData) o);
			
		} else {
			
			if(get(VConstants.xfull) == null){
				
				
				fmd.put(key,o);
			}
			
			
			else {
				
				fmd.initIfNeeded();
				HashMapData hmd=fmd.getData(getInt(VConstants.xfull),getInt( VConstants.yfull));
				if(o == null){
					hmd.removeAppropriate(key);
				} else {
					if( o instanceof MapData){
						hmd.putAppropriate((MapData)o);
					} else {
						hmd.put(key, o);
					}
					
					
				}
				
			}
		}
		
		
		
		
		//if value is string then get from map based on type
	}
	
	@Override
	public PBase clone() {
		
		return new PutMap().copyProperties(this);
	}
}
