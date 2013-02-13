package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class CityRule extends VParams {

	public CityRule() {
	}
	int number = 4;
	int count = 0;
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		if(number <= count){
			return;
		}
		MapData md=hmdMain.getParent().getNearKeyValue(VConstants.obstacle, "water", hmdMain, 2);
		MapData city=hmdMain.getParent().getNearKeyValue(VConstants.gate, "itemshop", hmdMain, 5);
		if(md != null&&city == null){
			count++;
			hmdMain.put(new SimpleMD(VConstants.gate,"itemshop"));
			hmdMain.getParent().getNearby(hmdMain, new GetForNearby<HashMapData>(hmdMain.getParent()) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				hashmapdata.put("nearcity", 5);
				return super.get(hashmapdata);
			}
			}, 2);
		}
		
		
	}


	@Override
	public PBase clone() {
		return new CityRule().copyProperties(this);
	}
}
