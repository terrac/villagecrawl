package gwt.client.map.getters;

import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

public class KeyValue implements IExecuteHash {
	Object value;
	String[] keys;
	private boolean not;

	public KeyValue(String key, Object value) {

		if (key.contains(".")) {
			keys = key.split("\\.");

		} else {
			keys = new String[] { key };
		}

		this.value = value;
	}
	public KeyValue(String key, boolean not) {
		this(key,null);
		this.not = not;
		
	}

	public KeyValue(String key) {
		this(key,null);		
		this.not = false;
	}
	
	@Override
	public boolean execute(HashMapData hmd) {
		Object md = null;
		if (keys != null) {
			Object pb = hmd;
			for (String k : keys) {
				if (pb == null || !(pb instanceof PBase)) {
					break;
				} else {
					pb = ((PBase) pb).get(k);
				}

			}
			md = pb;
		}

		if (not^(md != null && value == null)) {
			return true;
		}
		if(value == null){
			return false;
		}
		if (md != null) {
			if (md instanceof MapData) {
				if (not^value.equals(((MapData) md).getValue())) {
					return true;
				}
			}
			if (not^value.equals(md)) {
				return true;
			}

			return false;
		}
		return false;
	}
}
