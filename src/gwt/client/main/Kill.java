
package gwt.client.main;

import java.util.Map;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class Kill extends VParams {
	public Kill() {
		// TODO Auto-generated constructor stub
	}
	LivingBeing lb;
	
	public Kill(LivingBeing lb) {
		super();
		this.lb = lb;
	}

	@Override
	public void execute(Map<String, Object> map) {
		lb.death();
	}

}
