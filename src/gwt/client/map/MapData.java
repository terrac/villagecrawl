package gwt.client.map;

import gwt.client.item.Item;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.io.Serializable;

import javax.persistence.Transient;

import com.google.gwt.dev.util.collect.HashSet;

public abstract class MapData extends PBase implements Serializable {

	public MapData() {

	}

	public MapData parent;

	public MapData getParent() {
		return parent;
	}

	public void setParent(MapData parent) {
		// parent.addObject(this);
		this.parent = parent;
	}

	// public void addObject(MapData objects) {
	// }

	public String getValue() {
		return null;
	}

	public static String getImage(String v) {
		if (v != null) {
			v = v.replace(' ', '-');
		} else {
			return null;
		}
		//put(VConstants.image, "/images/" + v + ".png");
		return "/images/" + v + ".png";
	}
	public static String getImageString(String v) {
		if (v != null) {
			v = v.replace(' ', '-');
		} else {
			return null;
		}
		
		return "/images/" + v + ".png";
	}
	
	public String getImage() {
		String img = getS(VConstants.image);
		if (img == null) {
			img = getImage(getValue());
			put(VConstants.image, img);
		}
		return img;
	}

	public String getDescription() {
		String desc = getS(VConstants.description);
		if (desc == null) {
			desc = this.toString();
		}
		return desc;
	}

	// public Map<String,Object> expose(){
	// return null;
	// }

	// public void postEvent(String type, Object value,MapData mapdata){
	// getParent().consumeEvent(type,value,mapdata);
	// }
	// public void consumeEvent(String type, Object value, MapData mapdata) {
	//
	// }
	//
	// protected void postEvent(MapData md, String key) {
	// postEvent(new Event(md, key));
	//
	// }
	// protected void postEvent(Event event) {
	// getParent().postEvent(event);
	//
	// }
	// public Map<String,Object> getValues(){
	// return new HashMap<String, Object>();
	// }

	// signaling the passage of a turn
	public void update() {

	}

	public String getKey() {
		return (String) get(VConstants.name);
	}

	public String toString(int level) {
		return toString();
	}

	public MapData clone() {
		// grab all fields and do constructor that has all fields
		throw new IllegalArgumentException(
				"Clone is not supported for this object");
	}

	public void iterate(IFullMapExecute ifme) {

	}

	public void setParent(MapData fullMapData, int x, int y) {
		setParent(fullMapData);

	}

	static int count = 0;

	public List<String> getImageList() {
		return null;
	}

}
