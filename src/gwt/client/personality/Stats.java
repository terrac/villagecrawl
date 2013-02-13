package gwt.client.personality;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.io.Serializable;

public class Stats extends PBase{
	
	public Stats() {
		if(getSpeed() == 0){
			setSpeed(5);
		}
	
	}
	public int getSpeed() {
		return getInt(VConstants.speed);
	}


	public void setSpeed(int speed) {
		put(VConstants.speed,speed);
	}


	public int getNextTurn() {
		return nextTurn;
	}


	public void setNextTurn(int nextTurn) {
		
		this.nextTurn = nextTurn;
	}


	int stamina;
	int nextTurn;
	
	final static int nextTurnTop = 10;
	
	






	public int getStamina() {
		return stamina;
	}


	public void setStamina(int stamina) {
		this.stamina = stamina;
	}





	public void resetNextTurn() {
		//not a straight zero so that subtle speed differences can occur
		nextTurn =  nextTurn +nextTurnTop;
		
	}
	@Override
	public String toString() {
		String a = "Stats:";
		for(Entry<String, Object>  e : getObjMap().entrySet()){
			a += "\n"+e.getKey() + ": "+e.getValue();
		}
		return a;
	}
	
	@Override
	public PBase clone() {
		
		return new Stats().copyProperties(this);
	}
	public boolean isHealthy() {
		return !containsKey(VConstants.health)||getInt(VConstants.maxhealth) == getInt(VConstants.health);
		
	}
	public Integer getHealth() {
		Integer health = (Integer) get(VConstants.health);
		if(health == null){
			health =(Integer) get(VConstants.maxhealth);
		}
		return health;
	}


	
}
