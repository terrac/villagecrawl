package gwt.client.main.base;

import gwt.client.main.PTemplate;
import gwt.client.main.VConstants;
import gwt.client.state.State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class ActionHolder extends PBase{
	 //public List<State> statesNeeded = new ArrayList<State>(); 
	 
	
	 public ActionHolder() {
	
	}
	public String getAction() {
		return getS(VConstants.action);
	}


	public ActionHolder(String action) {
		super();
		put(VConstants.action,action);
	}
//	public void addState(State st){
//		statesNeeded.add(st);
//	}
	@Override
	public String toString() {
		
		return getAction();
	}
	
	@Override
	public PBase clone() {
		// TODO Auto-generated method stub
		return new ActionHolder().copyProperties(this);
	}
}
