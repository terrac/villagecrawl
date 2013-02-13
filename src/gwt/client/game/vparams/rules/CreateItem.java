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

public class CreateItem extends OObject{
	public CreateItem() {
		
	}
	
	public CreateItem(String type){
		put(VConstants.type,type);
	}
	int count = 0;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		count++;
		if(count < 20){
			return new Returnable(true);
		}
		
		int radius =2;
		int xoff = VConstants.getRandom().nextInt(radius);
		int yoff = VConstants.getRandom().nextInt(radius);
		HashMapData hmd = person.getParent().getParent().getData(person.getX()+xoff, person.getY()+yoff);
		if(hmd == null){
			return null;
		}
		String owned = hmd.getS(VConstants.owned);
		
		if (owned != null) {
			Item item=ComplexCityGenerator.createItem(owned);
			hmd.getItemsCreate().add(item.clone());
			
		}
		count = 0;
		return null;
	}

	
	
	@Override
	public OObject clone() {
		
		return new CreateItem().copyProperties(this);
	}

}
