package gwt.client.game;

import gwt.client.main.VConstants;
import gwt.client.map.MapData;

public class VisualDamage extends GEvent {
	
	public VisualDamage() {
		// TODO Auto-generated constructor stub
	}
	String type;
	@Override
	public String getKey() {
		return VConstants.visualdamage;
	}
	 @Override
	public String getValue() {
		
		return type;
	}
	public VisualDamage(String type) {
		super();
		this.type = type;
	}
	
}
