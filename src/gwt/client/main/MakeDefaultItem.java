package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class MakeDefaultItem extends OObjectB {
	public MakeDefaultItem() {

	}

	int time;
	private String toMake;

	public MakeDefaultItem(int time, String toMake, Item item) {
		super();
		this.time = time;
		this.toMake = toMake;
		this.item = item;
	}

	Item item;

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {

		addToList(person, new Wait(time));

		Item pitem = (Item) person.getParent().getItems().remove(toMake);

		if (toMake != null) {

			// the item is in the other persons property
			if (pitem == null) {
				pitem = (Item) person.remove(toMake);

			}
			if (pitem == null) {
				return new Returnable();
			}
		}

		Item it = item.clone();
		// person.getProperty().add(it);
		person.getParent().getItems().put(it);
		return new Returnable(false, 0);
	}

	@Override
	public OObject clone() {

		return new MakeDefaultItem(time, toMake, item);
	}
}
