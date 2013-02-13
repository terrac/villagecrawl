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

public class NeedRule extends VParams {
	public NeedRule() {

	}

	public NeedRule(Object... objects) {
		setPBase(this, objects);
	}

	@Override
	public void execute(Map<String, Object> map) {
		Item it = (Item) map.get(AttachUtil.OBJECT);
		int calcvalue = 0;
		for(String type :it.getTypes()){
			calcvalue=getInt(type);
			if(calcvalue != 0){
				it.setCalculatedValue(calcvalue* it.getItemValue());
				break;
			}
		}
		// if the item has a needed type
		// set the value
		
	}

	@Override
	public VParams clone() {

		return new NeedRule().copyProperties(this);
	}

}
