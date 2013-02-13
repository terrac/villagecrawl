package gwt.client.item;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;



public class ItemBuildProperties  implements Serializable{
	public ItemBuildProperties() {
	
	}
	 public String[] toMake;
	public ItemBuildProperties(String[] toMake, int time) {
		super();
		this.toMake = toMake;
		this.time = time;
	}
	 public int time;
	public Object oobject;
}
