package gwt.client.state;

import gwt.client.main.Person;
import gwt.client.main.base.LivingBeing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class State implements Serializable{
	 String type;
	
	 public State() {
	}
	public State(String type) {
		super();
		this.type = type;
	}

	public  List<String> stateData = new ArrayList();

	

	
	public boolean gatherState(LivingBeing person) {
		return stateData.get(0).equals(person.getState(type));
	}



	@Override
	public String toString() {
		return "State [stateData=" + stateData + ", type=" + type + "]";
	}
}
