package gwt.client.main;

import java.util.Map.Entry;

import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.SymbolicMap;
import gwt.shared.ClientBuild;

public class PickUp extends OObject {

	public PickUp() {
	}
	
	
	public PickUp(String itemname) {
		super();
		put(VConstants.item,itemname);
	}

	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
		//should get on or next to 
		
		return new Returnable(false,0,!pickup(person,getS(VConstants.item)));
	}

	public static boolean pickup(LivingBeing person,String itemname) {
		if(person.getParent().getItems() == null){
			return false;
		}
		if(itemname == null&&person.getParent().getItems().size() > 0){
			
			Entry<String,Item> item=(Entry<String,Item>) person.getParent().getItems().getObjMap().entrySet().toArray()[0];
			DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(new UImage(item.getValue().getImage())));
			displayPopup.displaypopup(person, null,5);
			
			person.getItemsCreate().add(person.getParent().getItems());
			if(person.getParent() != null&&person.getParent().getItems() != null){
				person.getParent().getItems().clear();	
			}
			
			return true;
		}
		
		
		HashMapData hmd=(HashMapData) person.getVariable("movedestination");
		if(hmd == null){
			hmd = person.getParent();
		}
		Item item=(Item) hmd.getItems().getAndRemove(itemname);
		//Log.log("pickup", person+" "+item);
		if(item == null){
			return false;
		}
		
		//person.getProperty().add(item.getKey(),item);
		person.getItems().put(item);
		return true;
	}
	
	@Override
	public PickUp clone() {

		return new PickUp().copyProperties(this);
	}

}
