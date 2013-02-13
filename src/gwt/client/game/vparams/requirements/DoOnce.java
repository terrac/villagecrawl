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

public class DoOnce extends VParams{
	
	public DoOnce() {
	}
	
	public DoOnce(String name,String value,VParams vp) {
		put(VConstants.name,name);
		put(VConstants.value,value);
		put(VConstants.vparams,vp);
		put(VConstants.type,getClass().getName());
		
	}
	
	
	public void execute(java.util.Map<String,Object> map) {
		PBase quest = EntryPoint.game.getPBase(VConstants.quest);
		
		String value = getS(VConstants.value);
		Object name = quest.get(getS(VConstants.name));
		if(value==null&&name==null||value!=null&&value.equals(name)){
			map.put(VConstants.success, true);
		}
	}
	
	@Override
	public PBase clone() {
		
		return new DoOnce().copyProperties(this);
	}

}
