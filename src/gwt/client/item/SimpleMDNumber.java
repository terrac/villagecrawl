package gwt.client.item;

import gwt.client.map.MapData;

public class SimpleMDNumber extends SimpleMD {

	public SimpleMDNumber() {
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "SimpleMDNumber [number=" + number + ", type=" + getKey()
				+ ", value=" + getValue() + "]";
	}
	public SimpleMDNumber(String type, String value) {
		super(type, value);
		
	}
	int number = 0;
	public SimpleMDNumber(String type, String value, int number) {
		super(type, value);
		this.number = number;
	}
}
