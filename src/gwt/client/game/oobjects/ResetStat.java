package gwt.client.game.oobjects;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;

public class ResetStat extends OObject {

	public ResetStat() {

	}

	public ResetStat(String string) {
		put(VConstants.name, string);
	}

	@Override
	public Returnable execute(FullMapData fullMapData, final LivingBeing person) {
		String name = getS(VConstants.name);
		person.getStats().put(name, EntryPoint.game.getTurn()+600);

		//person.getEconomy().lowerPriority(person);
				return null;
	}

	@Override
	public OObject clone() {

		return new ResetStat().copyProperties(this);
	}
}
