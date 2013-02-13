package gwt.shared.datamodel;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class VExecute extends OObject{
	
	public VExecute() {
			
		}
	
	public VExecute(int size,OObject o) {
		put(VConstants.size,size);
		put(VConstants.oObject,o);
	}
	
	public void init(){
		
	}
	
	@Override
	public OObject clone() {
		
		return new VExecute().copyProperties(this);
	}
	int count = 0;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		count++;
		if(count < getInt(VConstants.size)){
			addToList(person, (OObject) get(VConstants.oObject));
			return new Returnable(true);
		}
		return null;
	}
}
