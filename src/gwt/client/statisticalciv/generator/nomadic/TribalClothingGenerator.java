package gwt.client.statisticalciv.generator.nomadic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class TribalClothingGenerator extends VParams {

	public TribalClothingGenerator() {
	}

	/**
	 * 
	 animal skin/grass skirt
	 *
	 */
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);

		//run through each person and give them an animal skin or a skirt
		
		//also give them a knife
	}

	@Override
	public PBase clone() {
		return new TribalClothingGenerator().copyProperties(this);
	}
}
