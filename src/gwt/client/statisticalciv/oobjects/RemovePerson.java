package gwt.client.statisticalciv.oobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.CreateRandom;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.StatisticalCiv;

public class RemovePerson extends OObject {
	
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		person.death();
		return null;
	}
	@Override
	public OObject clone() {
		return new RemovePerson().copyProperties(this);
	}

	
}
