package gwt.client.game.ability;

import gwt.client.EntryPoint;
import gwt.client.game.Attack;
import gwt.client.game.AttackEnemyMeta;
import gwt.client.game.TargetSelf;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class Check extends VParams {

	private static final String checkstamped = "checkstamped";

	public void onSuccess(LivingBeing lb, MapData md) {
		
		
		
		// get held ability
		PBase pb = getAbility();
		if(lb.getDisabled(pb.getS(VConstants.name))){
			return;
		}
		// get target off of ability
		String target = pb.getS(VConstants.target);

		
		OObject current = lb.getTemplate().getCurrent();
		if (current != null&&current.getTopOParent().containsKey(checkstamped)) {			
			return;
		}
		OObject oob;
		
		
		// if target is self then add targetself oobject
		if (VConstants.self.equals(target)) {
			oob = new TargetSelf(pb);
		}
//		else if (VConstants.enemy.equals(target) && md instanceof LivingBeing
//				&& md.get(VConstants.team).equals(lb.get(VConstants.team))) {
//			// if target is enemy then pull the nearest enemy and set on attack,
//			// (if the md is valid then use it)
//
////			HashMapData hmd = AttackEnemyMeta.getNearestEnemy(lb.getParent()
////					.getParent(), lb, 5);
////			if(hmd == null){
////				return;
////			}
//			oob = new Attack(lb, pb);
//
//		} 
		else
		{
			// else create attack action and set the action to apply
			
			if(md == null){
				oob = new Attack(lb, pb);
			} else {
				oob = new Attack((LivingBeing) md, pb);
			}
		}
		// add to template, remove current actions
		lb.getTemplate().clear();		
		lb.getTemplate().push(oob);

		oob.put(checkstamped, true);

	}

	public PBase getAbility() {
		String pbname = getS(VConstants.ability);
		PBase pb=(PBase) EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.ability).get(pbname);
		return pb;
	}
}
