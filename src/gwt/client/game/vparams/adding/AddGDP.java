package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;
import java.util.Map.Entry;

public class AddGDP extends VParams{
	public AddGDP() {
		
	}
	public AddGDP(int size) {
		put(VConstants.size,size);
	}
	
	public void execute(Map<String,Object> map) {
		EntryPoint.game.getHtmlOut().addGDP(getInt(VConstants.size));
		
	}
	
	@Override
	public PBase clone() {
		
		return new AddGDP().copyProperties(this);
	}

}
