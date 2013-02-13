package gwt.client.game;

import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.RunAway;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MetaOObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;

public class AttackEnemyMeta extends PBase implements MetaOObject {

	public static final String AESTAMPED = "aestamped";

	@Override
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject current, Returnable ret) {
		// TODO Auto-generated method stub

	}

	@Override
	public Returnable preExecute(FullMapData fullMapData,
			final LivingBeing person, OObject current) {

		if (current.getTopOParent().containsKey(AESTAMPED)) {			
			return new Returnable(true);
		}
		Integer sight = (Integer) person.getStats().get(VConstants.sight);
		if(sight == null){
			sight = 5;
		}
		HashMapData hmd = getNearestEnemy(fullMapData,person,sight);

		if (hmd == null) {
			return new Returnable(true);
		}
		LivingBeing livingBeing = hmd.getLivingBeing();
		if (livingBeing == null) {
			return new Returnable(true);
		}
		if(VConstants.neutral.equals(livingBeing.getTeam())){
			return new Returnable(true);
		}
		if (calculateSuccessfulAttack(person, livingBeing) < 10
				&& Point.distance(person, livingBeing) > 1) {
			RunAway runaway = new RunAway(livingBeing);
			runaway.put(AESTAMPED, true);
			OObject.addToList(person, runaway);
			return null;
		}

		Attack attack = new Attack(livingBeing,person);
		attack.put(AESTAMPED, true);
		person.getTemplate().clear();
		OObject.addToList(person, attack);

		return null;

	}

	public static HashMapData getNearestEnemy(FullMapData fullMapData, final LivingBeing person,int range){
		
		return fullMapData.getNearby(person,
				new GetForNearby<HashMapData>(fullMapData) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						LivingBeing lb = hashmapdata.getLivingBeing();
						if (lb != null) {
							if (lb.containsKey(VConstants.team)
									&& !lb.get(VConstants.team).equals(
											person.get(VConstants.team))) {

								return hashmapdata;
							}
						}

						return null;
					}
				}, range);
	}
	public static int calculateSuccessfulAttack(LivingBeing person,
			LivingBeing livingBeing) {
		if (person.getInt(VConstants.level) - person.getInt(VConstants.level) < -1) {
			return 0;
		}
		return 50;
	}

	@Override
	public PBase clone() {
		
		return new AttackEnemyMeta().copyProperties(this);
	}
}
