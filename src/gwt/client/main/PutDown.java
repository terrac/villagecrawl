package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.SymbolicMap;

public class PutDown extends OObject {

	String itemname;
	public PutDown() {
	}

	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
		 
		putDown(person,itemname);
		return new Returnable(false,0);
	}

	public PutDown(String itemname) {
		super();
		this.itemname = itemname;
	}

	public static void putDown(LivingBeing person,String itemname) {
		Item value = (Item) person.getItems().getAndRemove(itemname);
		if(value == null){
			return;
		}
		
		HashMapData hmd=(HashMapData) person.getVariable("movedestination");
		
		if(hmd == null){
			hmd = person.getParent();
		}
		
		hmd.getItems().put(value);

	}
	
	public static void putDown(LivingBeing person) {
		
		
		HashMapData hmd= null;//(HashMapData) person.getVariable("movedestination");
		
		if(hmd == null){
			hmd = person.getParent();
		}
		hmd.getItemsCreate().add(person.getItems());
		person.getItems().clear();
	}

}
