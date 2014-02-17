package gwt.client.statisticalciv.rules;

import gwt.client.game.OCommand;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.PeopleRule;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;

public class MysticalQuest implements PBaseRule {
	protected static final class FightVillage extends OObject {
		@Override
		public Returnable execute(FullMapData fullMapData, LivingBeing person) {

			HashMapData hmd = person.getParent();
			Demographics demo = DemographicRule.getDemo(hmd);
			if (demo == null) {
				return null;
			}
			HashMapData home = getHome(person);
			
			if (DemographicRule.isSameLeader(hmd, home)) {
				return null;
			}
			CultureTrade.spreadCulture(home, hmd);
			double multiplier = 2.0;
			int minPopulation = 20;
			boolean genocide = DemographicRule.getSingleton().hasTech(
					Demographics.genocide);
			boolean slavery = DemographicRule.getSingleton().hasTech(
					Demographics.slavery);

			if (genocide && VConstants.getRandom().nextDouble() < .3) {
				multiplier = 10.0;
				slavery = false;
			}
			if (slavery) {
				minPopulation = 100;
			}

			Age.kill(demo, Age.YOUNG_ADULT,
					person.getPopulation().getDouble(VConstants.size)
							* multiplier);
			RuleOfLaw.checkFailure(hmd);
			if (demo.getDouble(VConstants.size) < minPopulation) {
				
				DemographicRule.removeVillage(hmd);
				{
					DisplayPopup displayPopup = new DisplayPopup(
							ClientBuild.list(new UImage("/images/"
									+ VConstants.damage + "fireball.png")));
					displayPopup.displaypopup(person, 3);
				}
				if(slavery){
					DemographicRule.addVillage(hmd, home);
				}
			} else {
				HashMapData nearestFarm;
				nearestFarm = getNearestVillage(person);

				if (nearestFarm != null) {
					if (!isHated(nearestFarm, person)) {
						nearestFarm.getMapData(VConstants.gate)
								.getListCreate(VConstants.hatred)
								.add(home.getPosition());
					}
				}
				// take the name of the village of the person, and put it on
				// the hatred list for the attacked village
				// any village with a hatred only sends
				// people to attack that village
				{
					DisplayPopup displayPopup = new DisplayPopup(
							ClientBuild.list(new UImage("/images/"
									+ VConstants.damage + "sword.png")));
					displayPopup.displaypopup(person, 1);
				}
			}

			return null;
		}
	}

	public static boolean isHated(HashMapData nearestFarm, LivingBeing person) {
		return nearestFarm.getMapData(VConstants.gate)
				.getListCreate(VConstants.hatred)
				.contains(getHome(person).getPosition());
	}

	public static OCommand moveCombat = new OCommand() {

		@Override
		public boolean execute(OObject oo, final LivingBeing person) {
			FullMapData fullMapData = person.getParent().getParent();
			double conflict = .3;
			if (VConstants.getRandom().nextDouble() < conflict) {
				HashMapData hmd = fullMapData.getNearby(person,
						new GetForNearby<HashMapData>(fullMapData) {
							public HashMapData get(HashMapData hashmapdata) {
								if (hashmapdata.getLivingBeing() != null
										&& PeopleRule.isHuman(hashmapdata
												.getLivingBeing())
										&& !hashmapdata.getLivingBeing()
												.equals(person)) {
									return hashmapdata;
								}
								return null;
							}

						}, 2);
				if (hmd != null) {
					LivingBeing lbAttacked = hmd.getLivingBeing();
					PeopleRule.conflictDamage(person, lbAttacked, conflict);
					// maybe return
				}
			}
			return true;
		}
	};

	double amt;

	public MysticalQuest() {
		// TODO Auto-generated constructor stub
	}

	public MysticalQuest(double amt) {
		this.amt = amt;
	}

	int size = 0;

	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		// create lb
		// make it move randomly within a 5 space radius
		// if that radius is a good spot (random chance) then have them move
		// back
		// and start secondary quest of getting wives and starting a town
		// also do a random chance of killing some of them off
		// if random says no the living either come back in disgrace or become
		// hermits
		if (p.getDouble(Age.YOUNG_ADULT) < .03
				|| p.getDouble(VConstants.size) < 50) {
			return false;
		}
		doYoungMen(p, hmd);

		return true;
	}

	public static LivingBeing doYoungMen(PBase p, HashMapData hmd) {

		LivingBeing lb = addPerson(hmd);
		lb.getAlterHolder().put(VConstants.weapon, new Item("dagger"));
		lb.getAlterHolder().put(VConstants.body, new Item("Animal Skin"));
		lb.getPopulation().put(VConstants.type, SConstants.bandit);
		int amount = 10;
		lb.getPopulation().put(VConstants.size, amount);
		Age.remove(p, Age.YOUNG_ADULT, amount);
		OObject.setCurrent(lb, new SimpleOObject() {
			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				// bandits randomly pick a nearby spot and try to raid that farm
				// bandits have a % chance of moving back to their initial farm
				// after every raid
				// if they do they increment a raid counter which warrior
				// creation uses
				final HashMapData home = getHome(person);

				HashMapData hmd = fullMapData.getNearby(person,
						new GetForNearby<HashMapData>(fullMapData) {
							@Override
							public HashMapData get(HashMapData hashmapdata) {
								if (home.equals(hashmapdata)) {
									return null;
								}

								if (!hashmapdata.isBlock()
										&& VConstants.getRandom().nextDouble() < .05) {//
									return hashmapdata;
								}
								return null;
							}
						}, 5);

				if (hmd != null) {
					OobList oobList = new OobList(new Move(hmd, "raid",
							moveCombat), new Wait("happy", 1));
					put(VConstants.overlay, CultureTrade.getOverlay(home));
					addToList(person, oobList);
					// add to raid counter

					if (VConstants.getRandom().nextDouble() < .99) {
						if (VConstants.getRandom().nextDouble() < .1) {
							oobList.addNextAction(new RemovePerson());
						}
						if (fullMapData.getNearKeyValue(VConstants.gate,
								SConstants.farm, hmd, 3) == null) {
							DemographicRule.addVillage(hmd, home);
							oobList.addNextAction(new RemovePerson());
						} else {

							oobList.addNextAction(new FightVillage());

						}
						// announce return home
						// large movement, potential conflict
						// then move back to create village
						// new BasicStory("The young men return for wives")

					}
				}

				return new Returnable(true);
			}
		});
		return lb;
	}

	public static LivingBeing addPerson(HashMapData hmd) {
		String type = VConstants.human;
		if(DemographicRule.getDemo(hmd).hasTech(Demographics.giant)){
			type = "dwarf";
		}
		return RandomPersonCreation.addRandomPerson(hmd,
				type, type);
	}

	protected static void setHome(LivingBeing person) {
		person.getPopulation().put(VConstants.home,
				getNearestVillage(person).getPosition());
	}

	protected static HashMapData getHome(LivingBeing person) {
		Point p = (Point) person.getPopulation().get(VConstants.home);
		if (p == null) {
			setHome(person);

			p = (Point) person.getPopulation().get(VConstants.home);
		}
		return person.getParent().getParent().getData(p);
	}

	public static HashMapData getNearestVillage(LivingBeing person) {
		return person.getParent().getParent()
				.getNearKeyValue(VConstants.gate, SConstants.farm, person, 3);
	}
}
