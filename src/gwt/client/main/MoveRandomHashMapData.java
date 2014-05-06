package gwt.client.main;

import gwt.client.main.base.BaseMoveMap;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;

public class MoveRandomHashMapData extends BaseMoveMap<HashMapData> {

	public MoveRandomHashMapData(String descr) {
		super(1, descr);

	}

	public MoveRandomHashMapData(int scope, String descr) {
		super(scope, descr);
	}

	public MoveRandomHashMapData() {
		put(VConstants.radius, 1);
	}

	@Override
	protected HashMapData findNewMD(FullMapData fullMapData, LivingBeing person) {
		int count = 0;
		HashMapData md = null;
		while (md == null && count < 10) {
			int xr = VConstants.getRandom().nextInt(getRadius() * 2 + 1);
			int yr = VConstants.getRandom().nextInt(getRadius() * 2 + 1);
			xr -= getRadius();
			yr -= getRadius();
			count++;
			if (xr == 0 && yr == 0) {
				continue;
			}
			md = fullMapData.getData(person.getX() + xr, person.getY() + yr);
			if (md != null && md.isBlock()) {
				md = null;
			}
		}
		return md;
	}

	protected void addMove(LivingBeing person) {
		addToList(person, new Move(getToMove(), "moveto " + getDescr()));
	}

	@Override
	public OObject clone() {

		return (OObject) new MoveRandomHashMapData().copyProperties(this);
	}
}
