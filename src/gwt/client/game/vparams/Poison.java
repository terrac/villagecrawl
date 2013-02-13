package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.game.util.PUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class Poison extends VParams {

	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		int p = lb.getStats().getInt(VConstants.poison);
		if( p < 1){
			PBase pb=(PBase) map.get(AttachUtil.attacher);
			map.put(AttachUtil.remove, this);
			
		} else {
			int damage = p / 4;
			
			String health2 = VConstants.health;
			PUtil.add(lb,- damage, health2);
			
			
		}
	}

	
}
