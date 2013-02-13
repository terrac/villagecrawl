package gwt.client.map.getters;

import gwt.client.main.base.PBase;
import gwt.client.map.AreaMap;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;

public class FGetList extends GetForNearby<HashMapData> {

	private IExecuteHash[] iarray;

	public FGetList(IExecuteHash[] iarray, AreaMap am) {
		this.iarray = iarray;
		areaMap = am;
	}

	@Override
	public HashMapData get(HashMapData hashmapdata) {
		for (IExecuteHash ieh : iarray) {
			if (!ieh.execute(hashmapdata)) {
				return null;
			}
		}
		
		return hashmapdata;
	}

}
