package gwt.client.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;

public class BreakUpItem extends OObject {
	public BreakUpItem() {

	}

	int time;
	List<String> items = new ArrayList<String>();
	List<Integer> numbers = new ArrayList<Integer>();
	String startingItem;

	public BreakUpItem(int time, List<String> items, List<Integer> numbers,
			String startingItem) {
		super();
		this.time = time;
		this.items = items;
		this.numbers = numbers;
		this.startingItem = startingItem;
	}

	public BreakUpItem(int time, String startingItem, Object... itemsnums) {
		super();
		this.time = time;
		boolean on = true;
		for(Object a : itemsnums){
			if(on){
				items.add((String) a);
			} else {
				numbers.add((Integer) a);
			}
			on =!on;
		}
		this.items = items;
		this.startingItem = startingItem;
	}

	@Override
	public List<String> getNeeds() {
		return Arrays.asList(new String[] { startingItem });
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
		addToList(person, new Move(house.get(VConstants.sittingspot), "movetosittingspot"));
		int a = 0;
		for(String toMake: items){
			Item item = person.getItemsMap().get(toMake).clone();
			item.setAmount(numbers.get(a));
			person.getItems().put(
					item);
			
			a++;
		}
		
		OObject.addToList(person, new ReturnFood());

		return new Returnable(false, 0);
	}

	@Override
	public OObject clone() {

		return new BreakUpItem(time, items, numbers, startingItem);
	}
}
