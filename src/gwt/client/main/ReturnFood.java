package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;

public class ReturnFood extends OObjectB {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if(person.getItems().size() < 1){
			return null;
		}
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		MapData md=person.getParent().getMapData(VConstants.corpse);
		if(md != null){
			addToList(person, new PickUp(VConstants.corpse));
		}
		person.getTemplate().pending.add(new Move((HashMapData) house.get("storage"),"movehome"));
		addToList(person, new DropAll());
		person.getTemplate().pending.add(new CleanUp((HashMapData) house.get("storage")));
		return null;
	}

}
