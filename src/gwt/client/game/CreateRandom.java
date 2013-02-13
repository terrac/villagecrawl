package gwt.client.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class CreateRandom extends OObject{

	public CreateRandom(String ...strings ) {
		
		put(VConstants.list,Arrays.asList(strings));
	}
	public CreateRandom() {	
	}
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		List l = getListCreate(VConstants.list);
		MapData md=(MapData) l.get(VConstants.getRandom().nextInt(l.size()));
		HashMapData hmd=fullMapData.getNearestEmpty(person);
		hmd.putAppropriate(md);
		return null;
	}

	@Override
	public OObject clone() {
		
		return new CreateRandom().copyProperties(this);
	}
}
