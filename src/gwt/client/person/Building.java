package gwt.client.person;

import gwt.client.main.Person;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Building extends MapData{
	//public static String[] dontrecord = new String[]{"wall","door","gate","chair"};
	public Building() {
	
	}

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	int health = 0;
	
	
	public void update() {
		
		
	};
	Map<String,HashMapData> map = new HashMap<String, HashMapData>();
	String type;
	
	@Override
	public String getKey() {
		return type;
	}
	public Building(String type,FullMapData fmd,int xsize, int ysize) {
		super();
		this.type = type;
		List<String> t2=new ArrayList<String>();
		for(int a = 0; a < fmd.getXsize(); a++){
			for(int b = 0; b < fmd.getYsize(); b++){
				for(MapData md : fmd.getData(a,b).removableValues()){
					if(!t2.contains(md.getValue())){
						map.put(md.getValue(), fmd.getData(a,b));
						t2.add(md.getValue());
					}
				}
				
			}
		}
	}
	public FullMapData fmd;
	public Building(String type2,FullMapData fmd) {
		this.type = type2;
		this.fmd = fmd;
		
	}
	public HashMapData get(String key) {
		

		return (HashMapData) map.get(key);
	}

	public boolean put(String key, MapData md) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return map+"";
	}
}
