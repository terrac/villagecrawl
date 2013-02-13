package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.PersonChoiceDisplay;
import gwt.client.game.display.UIVParams;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class ClickSelect extends VParams {

	@Override
	public void execute(Map<String, Object> map) {
		if(map.get(AttachUtil.OBJECT) instanceof Point){
			return;
		}
		LivingBeing lb=(LivingBeing) map.get(AttachUtil.OBJECT);
		EntryPoint.game.getHtmlOut().setCurrentlyFollowedUnset(lb);
		
	}
	
	@Override
	public ClickSelect clone() {

		return new ClickSelect().copyProperties(this);
	}
}
