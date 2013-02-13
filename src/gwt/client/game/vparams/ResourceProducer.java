package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.base.LivingBeing;
import gwt.shared.datamodel.VParams;

public class ResourceProducer extends VParams {

	
	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		
		
	}
}
