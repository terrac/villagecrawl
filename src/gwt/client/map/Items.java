package gwt.client.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.item.Item;
import gwt.client.main.VConstants;

public class Items extends HMapData {

	public static final String multi = "/images/" + VConstants.multiItem
			+ ".png";

	@Override
	public String getImage() {
		return getValue();
	}

	@Override
	public String getValue() {
		if (size() == 0) {
			return null;
		}
//		if (size() > 1) {
//			return multi;
//		}
		for (Item it : values()) {
			return it.getImage();
		}
		return null;
	}

	@Override
	public String getKey() {

		return VConstants.items;
	}

	public boolean put(String key, MapData value) {
		if (containsKey(key)) {
			Item item = (Item) get(key);
			item.add((Item) value);
			return true;
		}
		putEvenIfNotEmpty(key, value);
		return true;
	}

	public void add(Items items) {
		for (MapData md : items.removableValues()) {
			add((Item)md);
		}
	}

	public Item getItem(String key) {

		return (Item) super.get(key);
	}

	public Items clone() {
		Items hmd = new Items();

		for (MapData md : values()) {
			hmd.put(md.clone());
		}
		return hmd;
	}

	public Collection<Item> values() {

		return (Collection<Item>) (Collection<?>) getObjMap().values();
	}

	public void add(int i, Item toAdd) {
		Item item = getItem(toAdd.getKey());
		if (item == null) {
			item = toAdd.clone();
			item.setAmount(1);
			put(item);
		}
		int amount = item.getAmount() + i;

		if (amount < 1) {
			remove(toAdd.getKey());
		} else {
			if (item == null) {
				item = toAdd.clone();
			}
			item.setAmount(amount);
		}

	}

	@Override
	public MapData remove(String key) {
		// TODO Auto-generated method stub
		MapData remove = (MapData) super.remove(key);
		if (size() == 0 && getParent() != null) {
			getParent().remove(this.getKey());
		}
		return remove;
	}

	@Override
	public void clear() {
		if (getParent() != null) {
			getParent().remove(this.getKey());
		}

		super.clear();
	}

	public Item getFirstByProperty(String property) {
		for (Item it : values()) {
			if (it.containsKey(property)) {
				return it;
			}
		}
		return null;
	}

	public void add(Item favor) {
		if (favor.getParent() != null) {
			favor.getParent().remove(favor.getKey());
		}

		Item item = getItem(favor.getKey());

		if (item != null) {

			item.add(favor);
		} else {
			put(favor);
		}
	}

	public void add(List<Item> iList) {
		for (Item i : iList) {
			add(i);
		}
	}

	public int getTotalValue() {
		int tv = 0;
		for (Item it : values()) {
			tv += it.getTotalValue();
		}
		return tv;
	}

	public Items subtractMoneyAndThenItems(int amount) {
		Items items = new Items();
		for (Item it : values().toArray(new Item[0])) {
			if (it.getB(VConstants.money)) {
				Integer amount2 = it.getAmount();
				while (amount > 0 && amount2 > 0) {
					items.add(1, it);
					add(-1, it);
					amount2--;
					amount -= it.getItemValue();
				}
			}
		}

	    subtractItems(amount, items);
		return items;
	}

	public Items subtractItems(int amount) {
		return subtractItems(amount, new Items());
	}

	private Items subtractItems(int amount, Items items) {
		
		for (Item it : values().toArray(new Item[0])) {
			if (it.getB(VConstants.money)) {
				continue;
			}
			Integer amount2 = it.getAmount();
			while (amount > 0 && amount2 > 0) {
				items.add(1, it);
				add(-1, it);
				amount2--;
				amount -= it.getItemValue();
			}
		}
		return items;
	}

	public int getMoneyAmount() {
		int amt = 0;
		for (Item it : values().toArray(new Item[0])) {
			if (it.getB(VConstants.money)) {
				amt += it.getAmount();
			}
		}
		return amt;
	}
}
