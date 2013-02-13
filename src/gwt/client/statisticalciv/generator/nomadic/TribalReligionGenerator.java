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

public class TribalReligionGenerator extends VParams {

	public TribalReligionGenerator() {
	}

	/**
	 * 
	 Dialog. (you can build acorns here) (what is the meaning of life: killing
	 * is the meaning of life, accumulating wealth is the meaning of life,
	 * advancing knowledege is the meaning of life)
	 * 
	 * 
	 * 1 the shaman holds a lot of practical knowledge (like how to make certain
	 * herbal remedies)
	 * 
	 * 2 the shaman walks around dispensing religious advice and healing
	 */
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);

	}

	@Override
	public PBase clone() {
		return new TribalReligionGenerator().copyProperties(this);
	}
}
