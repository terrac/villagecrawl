package gwt.client.item;

import gwt.client.game.AlterHolder;
import gwt.client.main.VConstants;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.PhysicalMapData;

import java.io.Serializable;
import java.util.List;

public class Item extends MapData implements Serializable {

	public static final String consumable = "consumable";
	public static final String amount = "amount";

	@Override
	public String[] editConfiguration() {

		return new String[] { VConstants.name, amount, consumable };
	}

	@Override
	public String[] editConfigurationTypes() {

		return new String[] { VConstants.string, VConstants.integer,
				VConstants.bool };
	}

	public Item() {

	}

	public Item(String name, int amount, int value) {
		setName(name);
		setAmount(amount);
		setItemValue(value);
	}

	public void setName(String name) {
		put(VConstants.name, name);
	}

	public String getName() {
		return (String) get(VConstants.name);
	}

	public void setConsumable(boolean b) {
		put(consumable, b);
	}

	public String itemBuildAction;

	public Integer getItemValue() {
		return (Integer) get(VConstants.value);
	}

	public Integer getAmount() {
		if (get(amount) == null) {
			setAmount(1);
		}
		return (Integer) get(amount);
	}

	public void add(Item value) {

		put(amount, getAmount() + value.getAmount());
		if (value.getParent() != null) {
			value.getParent().remove(value);
		}
	}

	public Item subtract(int value) {
		if( getAmount() -value< 0){
			value = getAmount();
		}
		put(amount, getAmount() -value);
		if (getParent() != null&&getAmount() == 0) {
			getParent().remove(this);
		}
		Item clone = this.clone();
		clone.setAmount(value);
		return clone;
	}

	@Override
	public Item clone() {
		Item item = new Item();
		return (Item) item.copyProperties(this);
	}

	public Items getParent() {
		// TODO Auto-generated method stub
		return (Items) super.getParent();
	}

	public Boolean isConsumable() {

		return getB(consumable);
	}

	@Override
	public String toString() {
		return getObjMap() + "";
	}

	public void setAmount(int amount2) {
		put(amount, amount2);
	}

	@Override
	public String getValue() {
		String basicType = getS(VConstants.basictype);
		if (basicType != null) {
			return basicType;
		}
		return getKey();
	}

	public void setItemValue(int i) {
		put(VConstants.value, i);
	}

	public Item grabOne() {
		setAmount(getAmount() - 1);
		if (getAmount() == 0) {
			getParent().remove(this);
		}
		Item it = clone();
		it.setAmount(1);
		return it;
	}

	public MapData getMParent() {

		return parent;
	}

	public int getTotalValue() {
		return getItemValue() * getAmount();
	}

	public String getPersonImage(String type) {
		return getImage("doll/" + type + "/" + getValue());
	}

	public String getType() {
		return getS(VConstants.type);
	}

	public List<String> getTypes() {
		return (List<String>) get(VConstants.type);
	}

	public int getCalculatedValue() {
		return getInt(VConstants.calculatedvalue);
	}

	public void setCalculatedValue(int calcvalue) {
		put(VConstants.calculatedvalue,calcvalue);
	}

}
