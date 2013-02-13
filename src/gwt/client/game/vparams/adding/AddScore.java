package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;
import java.util.Map.Entry;

public class AddScore extends VParams{
	public AddScore() {
		
	}
	public AddScore(String stat,int statNum) {
		put(stat,statNum);
	}
	public AddScore(Object ...objects){
		setPBase(this, objects);
	}
	public void execute(Map<String,Object> map) {
		LivingBeing lb = getLivingBeing(map);
		for(Entry<String,Object> ent : this.getObjMap().entrySet()){
			PBase type = EntryPoint.game.getType(VConstants.culture).getType(VConstants.stats);
			String key = ent.getKey();
			int base=type.getInt(key);
			Integer incv = (Integer)ent.getValue();
			type.put(key, base+ incv);
			
			PBase.increment(type,key,incv);
			PBase.increment(lb.getType(VConstants.temporary).getType(VConstants.stats),key,incv);
			
		}
		
		
	}
	
	@Override
	public PBase clone() {
		
		return new AddScore().copyProperties(this);
	}

}
