package gwt.client.game.vparams.requirements;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class MapKeyValue extends VParams{
	
	public MapKeyValue() {
	}
	
	public MapKeyValue(String name,String value,VParams vp,String message) {
		put(VConstants.name,name);
		put(VConstants.value,value);
		put(VConstants.vparams,vp);
		put(VConstants.message,message);
		put(VConstants.type,getClass().getName());
	}
	
	
	public void execute(java.util.Map<String,Object> map) {
		//search all of the people on the map for a specific name value
		for(FullMapData fmd :EntryPoint.game.getMapArea().getMap()){
			for(HashMapData hmd :fmd){
				String value = getS(VConstants.value);
				MapData name = hmd.getMapData(getS(VConstants.name));
				if(value==null&&name==null||name!=null&&value.equals(name.getValue())){
					map.put(AttachUtil.OBJECT, hmd);
					map.put(VConstants.success, true);
					return;
				}
			}
		}
	}
	
	@Override
	public PBase clone() {
		
		return new MapKeyValue().copyProperties(this);
	}

}
