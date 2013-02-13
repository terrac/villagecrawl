package gwt.client.main;

import gwt.client.collect.HashBiMap;
import gwt.client.item.SimpleMD;
import gwt.client.item.SimpleMDNumber;
import gwt.client.map.FullMapData;

import java.util.Arrays;
import java.util.List;





public class OneCharacterMap {
	
	public static String[] obstacles = new String[]{"wall","table"};
	public static HashBiMap<String, Character> bimapMD = HashBiMap.create();
	static HashBiMap<Character, String> iBimapMD;
	public static HashBiMap<String, Character> hBimapMD = HashBiMap.create();
	public static HashBiMap<Character, String> hIBimapMD = hBimapMD.inverse();
	
	static {
		
		bimapMD.put("chicken", 'h');

		bimapMD.put("wall", '@');
		bimapMD.put("chair", 'c');
		bimapMD.put(VConstants.livingbeing, 'p');
		bimapMD.put("milk", 'm');
		bimapMD.put("question", '?');
		bimapMD.put("door", 'd');
		bimapMD.put("cow", 'e');
		bimapMD.put("gate", 'g');
		bimapMD.put("grinding stone", 'G');
		bimapMD.put("wheat", 'w');
		
		bimapMD.put("river", 'r');
		bimapMD.put("rock", 'R');
		bimapMD.put("forest", 'F');
		
		bimapMD.put("wild onions", 'l');
		bimapMD.put("mushrooms", 'q');
		
		bimapMD.put("grass", '.');
		bimapMD.put("carrots", 'z');
		bimapMD.put("oats", 'a');
		 
		bimapMD.put("barley", 'j');
		bimapMD.put("sorgum", 'n');
		
		//these represent concepts but they also have an underlying object.  like the dining room
		//has a table piece under it.
		bimapMD.put("dining room", 'i');
		bimapMD.put("storage", 's');
		bimapMD.put("sunny", 'S');
		bimapMD.put(VConstants.sittingspot, 't');
		bimapMD.put("kitchen", 'k');
		
		bimapMD.put("butter churn", 'b');
		bimapMD.put("firepit", 'o');
		bimapMD.put("cheese press", 'Z');
		
		bimapMD.put("deer", 'D');
//		bimapMD.put("wolf", 'W');
		bimapMD.put("none", ' ');
		bimapMD.put("femaletoddler", '1');
		bimapMD.put("maletoddler", '2');
		bimapMD.put("femalechild", '3');
		bimapMD.put("malechild", '4');
		bimapMD.put("femaleadult", '5');
		bimapMD.put("maleadult", '6');
		bimapMD.put("femaleelder", '7');
		bimapMD.put("maleelder", '8');
		bimapMD.put("kiln", 'K');
		bimapMD.put("halfgrass", 'M');
		bimapMD.put("water", 'W');
		iBimapMD = bimapMD.inverse();
	}
	public static char get1CharRep(String key){
		Character a = hBimapMD.get(key);
		if(a != null){
			return a;
		}
		if(!bimapMD.containsKey(key)){
			return 'U';
		}
		return bimapMD.get(key);
	}
	public static String getString(Character key){
		String a=hIBimapMD.get(key);
		if(a != null){
			return a;
		}
		return iBimapMD.get(key);
		
	}


}
