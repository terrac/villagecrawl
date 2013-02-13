package gwt.client.main.base.under;

import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.CreateAndDecay;
import gwt.client.main.VConstants;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class Under extends MapData{
	
	

	@Override
	public String getKey() {
		return VConstants.under;
	}
	
	
	@Override
	public MapData clone() {
		return new Under().copyProperties(this);
	}
}
