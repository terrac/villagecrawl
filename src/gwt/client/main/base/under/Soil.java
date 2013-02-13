package gwt.client.main.base.under;

import gwt.client.main.VConstants;
import gwt.client.map.MapData;

public class Soil extends Under{
	public int nutrients = 100;

	@Override
	public String getKey() {
		
		return VConstants.under;
	}
	@Override
	public String getValue() {
		
		return VConstants.soil;
	}
	@Override
	public Soil clone() {

		return new Soil(); 
	}
}
