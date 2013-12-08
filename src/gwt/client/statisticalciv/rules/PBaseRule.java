package gwt.client.statisticalciv.rules;

import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public interface PBaseRule {
	public boolean run(PBase p,HashMapData hmd, FullMapData fmd);

}
