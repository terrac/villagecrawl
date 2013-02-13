package gwt.client.main;


import gwt.client.main.base.LivingBeing;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;


public class GetResource extends OObjectB {

	private String toType;
	private String key;
	public GetResource() {
	}
	public GetResource(String type,String key,String toType) {
		super();
		this.type = type;
		this.key = key;
		this.toType = toType;
	}
	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
		//should eventually move around
		HashMapData hmd=person.getParent().getParent().getNearKeyValueUnoccupied(key,toType, person, 40);
		addToList(person,new Move(hmd, "to"+type));

//		MakeItem makeItem = new MakeItem(person.getItemsMap().get(type));
//		addToList(person, makeItem);
		
		return new Returnable(false, 0);
	}
	 String type;
	

}
