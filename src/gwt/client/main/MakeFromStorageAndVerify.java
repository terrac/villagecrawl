package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.person.Building;

public class MakeFromStorageAndVerify extends OObject {
	public MakeFromStorageAndVerify() {
		// TODO Auto-generated constructor stub
	}

	Building building;
	String getFrom;
	String itemToGet;
	String toMakeAt;
	String toMake;
	String putTo;
	

	public MakeFromStorageAndVerify(Building building, String getFrom,
			String itemToGet, String toMakeAt, String toMake, String putTo) {
		super();
		this.building = building;
		this.getFrom = getFrom;
		this.itemToGet = itemToGet;
		this.toMakeAt = toMakeAt;
		this.toMake = toMake;
		this.putTo = putTo;
	}


	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if(!building.get(getFrom).getItems().containsKey(itemToGet)){
			return null;
		}
		
		person.getTemplate().pending.add(new Move((HashMapData) building.get(getFrom),"movestorage"));
		person.getTemplate().pending.add(new Carry(itemToGet,(HashMapData) building.get(toMakeAt)));
		//person.getTemplate().pending.add(new MakeItem(person.getItemsMap().get(toMake)));
		person.getTemplate().pending.add(new Carry(toMake,(HashMapData) building.get(putTo)));
		
		return null;
	}

}
