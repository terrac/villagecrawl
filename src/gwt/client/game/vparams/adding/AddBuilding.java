package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.AddNeed;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class AddBuilding extends VParams{
	public AddBuilding() {
		
	}
	
	public AddBuilding(String type, String action,String name){
		put(VConstants.type,type);
		put(VConstants.action,action);
		put(VConstants.name,name);
	}
	

	public void execute(Map<String,Object> map) {
		
		RandomEffects.addBuilding( getS(VConstants.type), getS(VConstants.action),getS(VConstants.name),EntryPoint.game.getHtmlOut().currentFMD,10);
	}
	
	@Override
	public PBase clone() {
		
		return new AddBuilding().copyProperties(this);
	}

}
