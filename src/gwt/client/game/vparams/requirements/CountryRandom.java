package gwt.client.game.vparams.requirements;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class CountryRandom extends VParams {
	
	@Override
	public void execute(Map<String, Object> map) {
		String type = (String)VConstants.getRandomFromList(EntryPoint.game.getPBase(VConstants.country).getList(VConstants.list));
		LivingBeing lb = getLivingBeing(map);
		String country=lb.getS(VConstants.country);
		TagGenerator.add(map, type);
	}
	@Override	
	public PBase clone() {
		
		return new CountryRandom().copyProperties(this);
	}
	
}
