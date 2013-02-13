package gwt.client.game.vparams;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

public class RemoveFood extends VParams{
	public void execute(java.util.Map<String,Object> map) {
		LivingBeing person = (LivingBeing) map.get(AttachUtil.OBJECT);
		HashMapData itemHMD = person.getParent();
		FoodGroup fg=itemHMD.getFoodGroup();
	
		if(fg != null){
			itemHMD.remove(VConstants.temporary);
		}
		
	};
	
	@Override
	public PBase clone() {
		
		return new RemoveFood();
	}

}
