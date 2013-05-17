package gwt.client.statisticalciv;

import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class GrowthRule extends VParams {

	public GrowthRule() {
	}

	@Override
	public void execute(Map<String, Object> map) {
//		LivingBeing person = getLivingBeing(map);
//		// take the growth of the tile and use it to increase each population
//		// according
//		// to their cultural/physical growth
//		int growth = person.getParent().getInt("growth");
//		PBase population;
//		try {
//			population = person.getPBase(VConstants.population);
//		} catch (ClassCastException e) {
//			population = ConflictRule.randomize(new PBase());
//			person.put(VConstants.population, population);
//		}
//		if (population == null) {
//
//			return;
//		}
//		int size = population.getInt(VConstants.size);
//		size += growth;
//		population.put(VConstants.size, size);

	}

	@Override
	public PBase clone() {
		return new GrowthRule().copyProperties(this);
	}
}
