package gwt.client.main;

import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.SymbolicMap;

public class DropAll extends OObject {

	
	

	public DropAll() {
	}

	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
//		if(!AttachUtil.run(DROPALL, person.getParent(), person.getMapArea())){
//			return null;
//		}
		PutDown.putDown(person);
		return new Returnable(false,0);
	}

}
