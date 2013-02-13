package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.output.OutputDirector;

public class Say extends OObject {
	public Say() {
		// TODO Auto-generated constructor stub
	}

	String say;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		System.out.println(""+person.getName()+": "+say);
		return null;
	}
	public Say(String say) {
		super();
		this.say = say;
	}

	@Override
	public OObject clone() {
		
		return new Say(say);
	}
}
