package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VisualDamage;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class ConflictRule extends VParams {

	private VParams cityRule = new CityRule();

	public ConflictRule() {
		// images.add(new ArrayList<String>(Arrays.asList(new String[]
		// {"full-red"})));
	}
	
	public ConflictRule(int growthIteration) {
		//put("growthIteration",growthIteration);
	}

	@Override
	public void execute(Map<String, Object> map) {
		for (LivingBeing person : getFMD(map).people.toArray(new LivingBeing[0])) {

			HashMapData parent = person.getParent();
//			int growth = parent.getInt("growth");

			PBase p = person.getPBase(VConstants.population);
			if (p == null) {
				return;
			}
			double size = PBase.getDouble(p,VConstants.size);

			if (size <= 0) {
				person.death();
				return;
			}
			//PBase techStats = p.getType(VConstants.technology);
			int growthIteration = TechnologyRule.getDefaultInt(VConstants.person,person.getType(),VConstants.maxsize, 1000);
			p.put("totalsize", growthIteration);
			if (size > growthIteration) {
				p.put(VConstants.size, growthIteration);
				size = growthIteration;
			}
			// debug code really
			person.getStats().put(VConstants.health,(int) size);
			person.getStats().put(VConstants.maxhealth, growthIteration);

			if (PeopleRule.isHuman(person)) {
				if (size == growthIteration) {
					PBase p2 = p.clone();
					double hsize = size / 2;
					randomize(p2);
					p2.put(VConstants.size, hsize);
					p.put(VConstants.size, hsize);
					HashMapData hmd = parent.getParent()
							.getNearestEmpty(person);
					if (hmd != null) {
						hmd.putAppropriate(addPerson(p2));
					}
				}
			} else {
				if (size == growthIteration) {

					PBase p2 = p.clone();
					double hsize = size / 2;
					p2.put(VConstants.size, hsize);
					p.put(VConstants.size, hsize);
					HashMapData hmd = parent.getParent()
							.getNearestEmpty(person);
					if (hmd != null) {
						hmd.putAppropriate(addAnimal(p2));
					}
				}
			}

			// it checks whether or not two people are next to each other
			// and determines how they interact
			// it also determines when a population splits
		}
	}

	private MapData addPerson(PBase p2) {
		LivingBeing lb = PeopleRule.addPerson();
		lb.put(VConstants.population, p2);
		return lb;
	}

	private MapData addAnimal(PBase p2) {
		LivingBeing lb = FoodRule.addCow();
		lb.put(VConstants.population, p2);
		return lb;
	}

	public static int getTPop(List<PBase> population) {
		int tPop = 0;
		for (PBase p : population) {
			int size = p.getInt(VConstants.size);
			tPop += size;
		}
		return tPop;
	}

	static List<List<String>> images = new ArrayList<List<String>>();
	static List<String> list = new ArrayList(Arrays.asList(new String[] {
			"base", "body", "hair", "weapon" }));

	static {
		images.add(new ArrayList<String>(Arrays.asList(new String[] {
				"human female", "human male", "dwarf female", "dwarf male",
				"kobold female", "kobold male", "merfolk female",
				"merfolk male" })));
		images.add(new ArrayList<String>(Arrays.asList(new String[] {
				"leather armor", "black coat", "animal skin",
				"green breastplate", "dress", "robe", "rags" })));
		images.add(new ArrayList<String>(Arrays.asList(new String[] {
				"arwen-hair", "brown-hair", "fem_red", "green", "long_black",
				"pigtail_red", "short_black", "short-red", "short-white" })));
		images.add(new ArrayList<String>(Arrays.asList(new String[] { "axe",
				"dagger", "hammer", "katana", "pick_axe", "sickle", "scythe",
				"scimitar" })));

	}

	public static PBase randomize(PBase p2) {

		List<String> imagelist = new ArrayList<String>();

		int c = 0;
		for (String a : list) {
			List<String> l = images.get(c);
			String name = VConstants.getRandomFromList(l);
			imagelist.add(MapData.getImage("doll/" + a + "/" + name));
			c++;
		}
		p2.put(VConstants.imagelist, imagelist);
		return p2;
	}

	public static HashMapData findNewMD(FullMapData fullMapData, IPhysical iphys) {
		int radius = 1;
		int count = 0;
		HashMapData md = null;
		while (md == null && count < 10) {
			int xr = VConstants.getRandom().nextInt(radius * 2 + 1);
			int yr = VConstants.getRandom().nextInt(radius * 2 + 1);
			xr -= radius;
			yr -= radius;
			if (xr == 0 && yr == 0) {
				continue;
			}
			md = fullMapData.getData(iphys.getX() + xr, iphys.getY() + yr);
			count++;
		}
		return md;
	}

	@Override
	public PBase clone() {
		return new ConflictRule().copyProperties(this);
	}

	public static void checkDeath(LivingBeing person) {
		if(PBase.getDouble(person.getPopulation(),VConstants.size) <= 0){
			person.death();
		}
	}
}
