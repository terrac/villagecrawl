package gwt.client.main;

import gwt.client.map.MapData;

public class MapCountType extends MapData{
	public MapCountType() {
		// TODO Auto-generated constructor stub
	}

	public MapCountType(String type) {
		super();
		this.type = type;
	}

	String type;
	
	int count;
}
