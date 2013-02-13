package gwt.client.main.base;

import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;


public class StatemanagementMeta implements MetaOObject,Serializable {

	public StatemanagementMeta() {
	
	}
	@Override
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject current, Returnable ret) {
		if(current.getNeeds() == null){
			return;
		}
		Collection<String> needlist=(Collection<String>) person.getVariable(VConstants.needs);
		if(needlist == null){
			needlist = new HashSet<String>();
			person.setVariable(VConstants.needs, needlist);
		}
		
		for(String a:current.getNeeds()){
			needlist.add(a);
		}
	}

	@Override
	public Returnable preExecute(FullMapData fullMapData, LivingBeing person,
			OObject current) {
		//haven't implement parent for this yet, but it should still be called on the statemanagementaction
		return null;
	}

}
