package gwt.client.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;

public class MakeComplexItem extends OObject {
	public MakeComplexItem() {

	}


	public int getTime() {
		return getInt(VConstants.time);
	}

	public int getTimewait() {
		return getInt(VConstants.timewait);
	}

	public String getToMake() {
		return getS(VConstants.make);
	}

	public List<String> getNamesorproperties() {
		return getListCreate(VConstants.namesorproperties);
	}

	@Override
	public List<String> getNeeds() {
		
		return getListCreate(VConstants.namesorproperties);
	}
	public MakeComplexItem(int time, int timewait, String toMake,
			String... namesorproperties) {
		super();
		put(VConstants.time,time);
		put(VConstants.timewait,timewait);
		put(VConstants.make,toMake);
		put(VConstants.namesorproperties,namesorproperties);
		
		
	}

//	@Override
//	public List<String> getNeeds() {		
//		return Arrays.asList(namesorproperties);
//	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {

		addToList(person, new Wait(getTime()));

		List<Item> ilist = new ArrayList<Item>();
		Items items = person.getParent().getItems();
		if(items == null){
			return null;
		}
		for(MapData item : items.values()){
			String name=(String) item.get(VConstants.name);
			//could optimize this
			
			
			for(String a : getNamesorproperties()){
				if(a.equals(name)){
					ilist.add((Item) item);
					break;
				}
				if(a.equals(item.get(VConstants.type))){
					ilist.add((Item) item);
					break;
				}
				
			}
			
		}
		for(MapData item : person.getItems().values()){
			String name=(String) item.get(VConstants.name);
			//could optimize this
			
			
			for(String a : getNamesorproperties()){
				if(a.equals(name)){
					ilist.add((Item) item);
					break;
				}
				if(a.equals(item.get(VConstants.type))){
					ilist.add((Item) item);
					break;
				}
				
			}
			
		}
		for(Item item : ilist){
			//only destroy consumables might want to eventually verify tha the other items are there
			//and kick out if not
			if(!item.isConsumable()){
				continue;
			}
			items.remove(item);
			person.getItems().remove(item);
		}
		

		
		Item item = person.getItemsMap().get(getToMake());
		if(item == null){
			throw new IllegalArgumentException(getToMake() +" missing from itemmap");
		}
		person.getItems().put(item.clone());
		OObject.addToList(person, new ReturnFood());
		
		return new Returnable(false, 0);
	}

//	@Override
//	public OObject clone() {
//
//		return new MakeComplexItem(time,timewait, toMake, getNamesorproperties());
//	}
	@Override
	public OObject clone() {
		
		return new MakeComplexItem().copyProperties(this);
	}
}
