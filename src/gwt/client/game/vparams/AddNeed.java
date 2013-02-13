package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class AddNeed extends VParams{
	public AddNeed() {
		
	}
	public AddNeed(String need,int minstate, int maxstate) {
		put(VConstants.need,need);
		put(VConstants.minimum,minstate);
		put(VConstants.maximum,maxstate);
	}
	public void execute(Map<String,Object> map) {
		//((Economy)EntryPoint.game.getPBase(VConstants.economy)).addNeed(getS(VConstants.need),getInt(VConstants.minimum),getInt(VConstants.maximum));
	}
	
	@Override
	public PBase clone() {
		
		return new AddNeed().copyProperties(this);
	}

}
