package gwt.client.main.base;

import java.io.Serializable;

import gwt.client.main.Returnable;
import gwt.client.map.FullMapData;
import gwt.client.map.SymbolicMap;

public interface MapObject extends Serializable{
	public Returnable execute(FullMapData fmd) ;
}
