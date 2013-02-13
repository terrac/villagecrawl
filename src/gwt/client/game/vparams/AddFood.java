package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.shared.datamodel.VParams;

public class AddFood extends VParams{
	public void execute(java.util.Map<String,Object> map) {
		LivingBeing person = (LivingBeing) map.get(AttachUtil.OBJECT);
		HashMapData itemHMD = person.getParent();
		FoodGroup fg=itemHMD.getFoodGroup();
	
		PercentageMap pm;
		String itemName = null;;
		if(fg != null){
			pm=(PercentageMap)get(fg.type);
			if(pm == null){
				pm = (PercentageMap) get(VConstants.defaultplant);
			}
//			PercentageMap seasonM =(PercentageMap) get(EntryPoint.game.getStateDataMap().get("Season"));
//			
//			itemName=pm.runCombinedPercentages(seasonM);
//			Item item=EntryPoint.game.getItemMap().get(itemName);
//			if(item != null){
//				Items it = new Items();
//				it.put(item.clone());
//				itemHMD.put(VConstants.temporary, it);
//			}
			
			
		}
		
	};
	
	@Override
	public PBase clone() {
		
		return new AddFood();
	}

}
