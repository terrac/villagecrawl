package gwt.client.main;

import gwt.client.map.MapData;

public class StringMapData extends MapData {
	
	public StringMapData() {
		// TODO Auto-generated constructor stub
	}

	String value;
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	public StringMapData(String value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return value;
	}

}
