package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.game.Attack;
import gwt.client.game.ability.Check;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class CheckAttack extends Check {
	public CheckAttack() {
		
	}
	public CheckAttack(PBase ability,String type) {
		put(VConstants.ability,ability.get(VConstants.name));
		put(VConstants.action,type);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		OObject current = lb.getTemplate().getCurrent();
		if(current != null&&current.instanceOf(Attack.class)){			
			onSuccess(lb,current.getTopOParent().getMapData(VConstants.livingbeing));
		}
	}
	@Override
	public PBase clone() {
		
		return new CheckAttack().copyProperties(this);
	}
}
