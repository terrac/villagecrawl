package gwt.client.main.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gwt.client.game.display.LogDisplay;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;

public class OobList extends OObject {

	public OobList() {
			
		}
	public OobList(Object ...objects) {
		List l = new ArrayList();
		l.addAll(Arrays.asList(objects));
		put(VConstants.list, l);
		
	}
	public OobList(String overlay,Object ...objects) {
		this(objects);
		put(VConstants.overlay,overlay);
	}
	
	public OobList(String overlay,int repeat,Object ...objects) {
		this(overlay,objects);
		put(VConstants.repeat,repeat);
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {

		for(Object o :(List<Object>) get(VConstants.list)){
			if(o instanceof String){
				addToList(person,person.getMapArea().getClone((String) o));	
			} else {
				addToList(person,(OObject) o);
			}
			
		}
		int repeat = getInt(VConstants.repeat);
		if(repeat> 0){
			repeat--;
			put(VConstants.repeat,repeat);
			LogDisplay.log(repeat, 2);
			return new Returnable(true);
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "OobList [list=" + get(VConstants.list) + "]";
	}
	@Override
	public OObject clone() {
		
		return (OObject) new OobList().copyProperties(this);
	}
	public void addNextAction(OObject move) {
		getListCreate(VConstants.list).add(move);
	}
}
