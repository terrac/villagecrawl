package gwt.client.statisticalciv;

import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

public class TechnologyRule extends VParams{

	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		//there is a technology tree and each one fills up over time according to the tech amount
		//when the tech pops a story plays and the cultural attributes for that one alter
		//tech then spreads these to other nearby populations
	}
	@Override
	public PBase clone() {
		return new TechnologyRule().copyProperties(this);
	}
}
