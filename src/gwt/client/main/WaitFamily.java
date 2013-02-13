package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class WaitFamily extends OObject {
	public WaitFamily() {
		// TODO Auto-generated constructor stub
	}

	int time;

	public WaitFamily(int time) {
		super();
		this.time = time;
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {

		for (LivingBeing lb : person.getGroup().getFamily()) {
			if (3 < Point.distance(lb.getParent(), person.getParent())) {
				time--;
				if (time < 0) {
					return new Returnable();
				}
				return new Returnable(true, 1);

			}
		}

		return null;

	}

	@Override
	public OObject clone() {

		return new WaitFamily(time);
	}
}
