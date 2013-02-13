package gwt.client.map;

import java.io.Serializable;

import gwt.client.main.Point;

public class AgeMapData extends MapData implements IAge{
	AgeData ageData = new AgeData();
	
	public int getAge(){
		return ageData.age;
	}
	public boolean age(){
		return ageData.age();
	}
	@Override
	public void setAge(int age) {
		ageData.age = age;
		
	}
	
}
