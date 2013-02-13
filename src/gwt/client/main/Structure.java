package gwt.client.main;
import java.util.List;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.Parameters;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.person.Building;


public class Structure extends OObject{
	String type = VConstants.pithouse;
	

	public void setParameters(Object... pars) {
		type = (String) pars[0];		 
	}
	@Override
	public Parameters getParameterTypes() {
		return new Parameters("foods",type);
	}
	public void init(String[] pars) {
		this.type = pars[0];
		
		
	}
	@Override
	public Returnable execute(FullMapData fmd, LivingBeing person) {
		if(person.getGroup() == null){
			return null;
		}
		Building house =(Building) person.getGroup().get(VConstants.pithouse);
	
		if(house == null){
			Build.build(person, fmd, type, 10, 0,false);
			//Build.build(person, fmd, "barn", 2, 10,false);
			

			person.put(VConstants.home, fmd);
			
		} 
		
		return new Returnable(false, 1);
	}
	@Override
	public OObject clone() {
	
		return new Structure();
	}
}
