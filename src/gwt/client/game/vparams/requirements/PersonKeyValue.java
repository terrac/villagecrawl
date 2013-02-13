package gwt.client.game.vparams.requirements;

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
import gwt.shared.datamodel.VParams;

public class PersonKeyValue extends VParams{
	
	public PersonKeyValue() {
	}
	
	public PersonKeyValue(String name,String value,VParams vp) {
		put(VConstants.name,name);
		put(VConstants.value,value);
		put(VConstants.vparams,vp);
		put(VConstants.type,getClass().getName());
	}
	
	
	public void execute(java.util.Map<String,Object> map) {
		//search all of the people on the map for a specific name value
		for(FullMapData fmd :EntryPoint.game.getMapArea().getMap()){
			for(LivingBeing lb :fmd.people){
				String value = getS(VConstants.value);
				Object name = lb.get(getS(VConstants.name));
				if(value==null&&name==null||value!=null&&value.equals(name)){
					map.put(AttachUtil.OBJECT, lb);
					map.put(VConstants.success, true);
					return;
				}
			}
		}
	}
	
	@Override
	public PBase clone() {
		
		return new PersonKeyValue().copyProperties(this);
	}

}
