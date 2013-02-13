package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.game.ability.Check;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class CheckDamage extends Check {
	public CheckDamage(PBase pb,int i,String team,String action) {
		put(VConstants.ability,pb.get(VConstants.name));
		put(VConstants.health,i);
		put(VConstants.action,action);
		put(VConstants.team,team);
	}
	public CheckDamage(PBase pb,int i,String action) {
		this(pb, i, null, action);
	}

	public CheckDamage() {

	}


	@Override
	public void execute(Map<String, Object> map) {
		

		// check health level of person

		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		
		String team = getS(VConstants.team);
		if(team != null){
			if(!team.equals(lb.get(VConstants.team))){
				return;
			}
		}
		if(!lb.getStats().containsKey(VConstants.health)){
			return;
		}
		double d = ((double)lb.getStats().getInt(VConstants.health))
				/ lb.getStats().getInt(VConstants.maxhealth);
		if (d * 100 < getInt(VConstants.health)) {
			map.put(VConstants.success, true);
			// if health is below % (or if that is not set then fire the action
			// associated
			
			LivingBeing obj = (LivingBeing) get(AttachUtil.OBJECT);
			if(obj != null){
				onSuccess(obj,lb);
			} else {
				onSuccess(lb,null);	
			}
			
			
			// either an actual vparam or an attachoobject vparam;
			
		}

	}

	@Override
	public PBase clone() {
		
		return new CheckDamage().copyProperties(this);
	}
}
