package gwt.client.statisticalciv.generator.nomadic;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

public class TribalPoliticsGenerator extends VParams {

	public TribalPoliticsGenerator() {
	}

	/**
	 * 
	 j fundamentalist vs liberal mindset on a degree, adds some extra
	 * statements on either side to the shaman
	 */
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		//hooked into the shamans talking
		
		//if 0
		//offer an occasional beating to keep in line
		
		//if 20
		//Offer some dissaproval of person X's behavior
		
		//if 90
		// advocate the need to change how
		
	}

	@Override
	public PBase clone() {
		return new TribalPoliticsGenerator().copyProperties(this);
	}
}
