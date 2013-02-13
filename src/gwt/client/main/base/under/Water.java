package gwt.client.main.base.under;

import gwt.client.main.VConstants;
import gwt.client.map.MapData;

public class Water extends Under{


	@Override
	public String getKey() {
		
		return VConstants.under;
	}
	@Override
	public String getValue() {
		
		return VConstants.water;
	}

	@Override
	public Water clone() {

		return new Water(); 
	}
}
