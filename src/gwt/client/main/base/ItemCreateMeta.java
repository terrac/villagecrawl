package gwt.client.main.base;

import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.PickUp;
import gwt.client.main.ReturnFood;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.person.Building;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class ItemCreateMeta extends PBase implements MetaOObject,Serializable {

	public ItemCreateMeta() {
	
	}
	@Override
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject current, Returnable ret) {
		
		
		
	}
	transient Move preexTo;
	
	
	@Override
	public Returnable preExecute(final FullMapData fullMapData,final LivingBeing person,
			final OObject current) {
		
		if(null !=current.get(VConstants.metarun)){
			//could add in a check for return food and see if the next one is there
			//
			return new Returnable(true, 0);
		}
		current.put(VConstants.metarun, true);
		
		if(current.getNeeds() ==null){
			
			 return new Returnable(true, 0);
		}
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		
		preexTo = null;
		final List<String> toPickup = new ArrayList();
		boolean needsMet = true;
		for(final String need:current.getNeeds()){
			//search all items within a 40 radius for a property matching the need
			//(either the value of a name or as an actual name)
			needsMet = null!=fullMapData.getNearby(person, new GetForNearby<HashMapData>() {

				@Override
				public HashMapData get(int x1, int y1) {

					Item pitem = null;
					HashMapData hmd = fullMapData.getData(x1,y1);
					for(MapData item : hmd.getItems().values()){
						String name=(String) item.get(VConstants.name);
						if(need.equals(name)){
							pitem = (Item) item;
							
						}
						if(need.equals(item.get(VConstants.type))){
							pitem = (Item) item;
							
						}
					}
					MapData md=hmd.getMapData(VConstants.gate);
					if(md != null&&md.getValue().equals(need)){
						preexTo= new Move(hmd,"movetosimplemd");
						return hmd;
					}
					if(pitem == null){
						//add to the family ggroup the need and a number (adding if exists)
						//higher number higher pirority
						return null;
					}
					if(VConstants.fire.equals(need)){
						//maintain fire.  if low or nonexistant
						//then create. 
						
						//to start with just move to firepit
						preexTo= new Move((HashMapData) pitem.getParent().getParent(),"movetofire");
						return hmd;
					}
					String type=(String) pitem.get(VConstants.type);
					if(type != null&&type.equals(VConstants.large)){
						//move to item
						
						preexTo= new Move((HashMapData) pitem.getParent().getParent(),"movetolargeitem");
						return hmd;
					}
					//go to storage and pick up item
					toPickup.add(pitem.getKey());
					
					
					
					return hmd;
				}
			}, 40);			
			
			if(!needsMet){
				break;
			}
		}
		
		//add all of these before the current oobject  do it by clearing and adding it last
		person.getTemplate().stack.pop();
		if(!needsMet){
			return null;
		}
		//add move to storage if list isn't empty
		//
		if(toPickup.size() > 0){
			OObject.addToList(person, new Move(house.get("storage"),"movetostorage"));
			for(String a :toPickup){
				OObject.addToList(person, new PickUp(a));
				
			}
		}
		if(preexTo != null){
			OObject.addToList(person,preexTo);
		}
		
		OObject.addToList(person, current);
		return null;
		
	}

	@Override
	public PBase clone() {
		
		return new ItemCreateMeta().copyProperties(this);
	}
}
