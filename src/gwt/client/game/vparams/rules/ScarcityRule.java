package gwt.client.game.vparams.rules;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.AddNeed;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Economy;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class ScarcityRule extends VParams {
	public ScarcityRule() {

	}

	public ScarcityRule(int count) {
		put(VConstants.count,count);
	}

	@Override
	public void execute(Map<String, Object> map) {
		Item it = (Item) map.get(AttachUtil.OBJECT);
		int calcvalue = 0;
		
		//if the person has a low amount of the item then set value
		LivingBeing lb = getLivingBeing(map);
		Item item = lb.getItemsCreate().getItem(it.getKey());
		if(item != null && item.getAmount() > getInt(VConstants.count)){
			//maybe eventually bias towards scarce a little
			it.setCalculatedValue(it.getItemValue());
			
		}
		
	}

	@Override
	public VParams clone() {

		return new ScarcityRule().copyProperties(this);
	}

}
