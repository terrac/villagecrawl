package gwt.client.main;

import java.util.ArrayList;
import java.util.List;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;

public abstract class OObjectB  extends OObject{

	public OObjectB() {
		super();
	}
	List<String> needs = new ArrayList<String>();
	
	public List<String> getNeeds() {
		return needs;
	}
	public void addNeed(String need) {
		// if someone is being a follower do not register needs.
		if(getTParent() == null){
			return;
		}
		needs.add(need);
	}

}