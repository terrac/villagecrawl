package gwt.client.main.base;

import java.util.Collection;
import java.util.Map;

import gwt.client.main.PTemplate;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;

public class StateMangementAction extends OObject {
	Map<String,PTemplate> leaderMap;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		//take the gathered needs and run them against the locally held mapped leaders
		Collection<String> needSet=(Collection<String>) person.getVariable(VConstants.needs);
//		if(needSet == null){
//			return null;
//		}
		for(String a : needSet){
			PTemplate pt=leaderMap.get(a);
			person.getTemplate().setRationalChild(pt.getTemplateName(), pt.getTemplateName());
		}
		return null;
	}

}
