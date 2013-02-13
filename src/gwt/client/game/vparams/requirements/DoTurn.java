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

public class DoTurn extends VParams{
	
	public DoTurn() {
	}
	
	public DoTurn(String name,int turn,VParams vp) {
		put(VConstants.name,name);
		put(VConstants.turn,turn);
		put(VConstants.vparams,vp);
		put(VConstants.type,getClass().getName());
		
	}
	
	
	public void execute(java.util.Map<String,Object> map) {
		if(EntryPoint.game.getTurn() >= getInt(VConstants.turn)){
			map.put(VConstants.success, true);
		}
	}
	
	@Override
	public PBase clone() {
		
		return new DoTurn().copyProperties(this);
	}

}
