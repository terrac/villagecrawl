package gwt.client.main;

import gwt.client.map.HashMapData;

public class Util {

	public static <T>T get(HashMapData hmd, String type){
		
		return(T) hmd.get(type);
	}
}
