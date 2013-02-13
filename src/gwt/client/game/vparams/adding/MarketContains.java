package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.item.Item;
import gwt.client.main.Economy;
import gwt.client.main.MoveClosest;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class MarketContains extends VParams{
	public MarketContains(String string,String message) {
		put(VConstants.item,string);
		put(VConstants.message,message);
	}
	
	public MarketContains() {
	}

	@Override
	public void execute(Map<String, Object> map) {
		Items it=(Items) EntryPoint.game.getPBase(VConstants.market).get(VConstants.items);
		if(it.containsKey(getS(VConstants.item))){
			map.put(VConstants.success, true);
		}}
	
	
	@Override
	public VParams clone() {
		
		return new MarketContains().copyProperties(this);
	}

	

}
