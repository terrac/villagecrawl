package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class SetTemplate extends VParams {
	public SetTemplate() {

	}

	public SetTemplate(String template) {
		put(VConstants.type, template);
	}

	public SetTemplate(String name,String template) {
		put(VConstants.name,name);
		put(VConstants.type, template);
	}

	@Override
	public void execute(Map<String, Object> map) {
		String name = getS(VConstants.name);
		LivingBeing livingBeing = getLivingBeing(map);
		
		if(name != null){
			for(LivingBeing lb : EntryPoint.game.getHtmlOut().currentFMD.people){
				if(name.equals(lb.getS(VConstants.name))){
					livingBeing = lb; 
				}
			}
		}
		String type = getS(VConstants.type);
		livingBeing.setTemplate(type);

	}

	@Override
	public PBase clone() {

		return new SetTemplate().copyProperties(this);
	}
}
