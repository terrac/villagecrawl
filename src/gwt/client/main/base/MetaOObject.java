package gwt.client.main.base;

import java.io.Serializable;

import gwt.client.main.Returnable;
import gwt.client.map.FullMapData;

public interface MetaOObject extends Serializable{
	public Returnable preExecute(FullMapData fullMapData, LivingBeing person, OObject current);
	
	public void postExecute(FullMapData fullMapData, LivingBeing person, OObject current, Returnable ret);

}
