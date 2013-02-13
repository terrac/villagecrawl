package gwt.client.map.runners;

import gwt.client.map.AreaMap;
import gwt.client.map.MapData;


public abstract class GetForNearby<T extends MapData> {

	public GetForNearby() {
	
	}
	protected AreaMap areaMap;
	public GetForNearby(AreaMap am) {
		super();
		this.areaMap = am;
	}


	public  T get(int x1, int y1){
		return get((T) areaMap.getData(x1, y1));
	}

	
	public  T get(T hashmapdata){return null;}
}