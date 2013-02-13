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

public class LaborRule extends VParams{
	public LaborRule() {
		
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		Item it = (Item) map.get(AttachUtil.OBJECT);
		int calcvalue = it.getItemValue();
		
		//just get the value and set the calculated value
		it.setCalculatedValue(calcvalue);
	}
		
	
	@Override
	public VParams clone() {
		
		return new LaborRule().copyProperties(this);
	}

}
