package gwt.client.personality;

import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.main.Person;
import gwt.client.main.RunAway;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;

import java.util.List;

import java.io.Serializable;

public class ImpulsivePersonality implements Serializable{

	public static void runAI(List<MapData> difadds, LivingBeing per) {
		//might want to do as meta somehow?
		if(difadds == null){
			return;
		}
		if(!per.isPerson()){
			if(!"deer".equals(per.getValue())){
				return;
			}
			
			for (MapData md : difadds){
				boolean a= md instanceof Person;
				if(a||"wolf".equals(md.getValue())){
					if(per.getVariable(VConstants.runaway) == null){
						per.getTemplate().stack.clear();
						per.getTemplate().stack.push(new RunAway((IPhysical)md));
					}
				}
				
			}
			
			return;
		}
		for (MapData md : difadds){
			if("wolf".equals(md.getValue())){
				per.getTemplate().stack.clear();
				per.getTemplate().stack.push(new  HuntAnimal());
			}
		}
		
		
	}
		
	
	

	
}
