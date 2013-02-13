package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;

public class CleanUp extends OObject {

	public CleanUp() {
		// TODO Auto-generated constructor stub
	}
	public CleanUp(HashMapData hashMapData) {
		hmd = hashMapData;
	}
	HashMapData hmd;
	@Override
	public Returnable execute(FullMapData fullMapData, final LivingBeing person) {
		fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
			
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.getItems().size() > 0&&!person.getParent().equals(hashmapdata)){
					addToList(person, new Move(hashmapdata, "toclean"));
					addToList(person, new PickUp());
					addToList(person, new Move(hmd, "tocleanback"));
					addToList(person, new DropAll());
					
				}
				return null;
			}
		}, 2);
		return null;
	}

}
