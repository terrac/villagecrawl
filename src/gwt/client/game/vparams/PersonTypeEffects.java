package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import java_cup.shift_action;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.shared.datamodel.VParams;

public class PersonTypeEffects extends VParams {

	public PersonTypeEffects() {
		// TODO Auto-generated constructor stub
	}

	int count = 500;

	@Override
	public void execute(Map<String, Object> map) {
		count++;
		if (count < 90) {

			return;
		}

		LivingBeing lb = VConstants.getRandomFromList(EntryPoint.game
				.getHtmlOut().currentFMD.people);
		if (!lb.containsKey(VConstants.owned)) {
			return;
		}
		count = 0;
		if(!lb.containsKey(VConstants.lines)){
			lb.getType(VConstants.lines).getListCreate(VConstants.type).add(VConstants.getRandomFromList(EntryPoint.getCulture(VConstants.lines).getList(VConstants.list)));
		}
		String type=(String)VConstants.getRandomFromList(lb.getType(VConstants.lines).getListCreate(VConstants.type));
		PBase pBase = EntryPoint.getCulture(VConstants.lines).getPBase(type);
		List list = pBase.getList(VConstants.list);
		String line=(String)VConstants.getRandomFromList(list);
		LogDisplay.log(lb.getName()+": "+line, 2);
		
	}

	@Override
	public PBase clone() {

		return new PersonTypeEffects();
	}

}
