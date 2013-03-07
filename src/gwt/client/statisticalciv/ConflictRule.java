package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);

		// If the growth is larger than the area can sustain then split
		// (includes cultural and physical growth in total sustaining ability)
		int growth = hmdMain.getInt("growth");

//		int growthIteration = 9 * growth + 200;
//		if(!hmdMain.containsKey("nearcity")){
//			growthIteration =growthIteration/9;
//		}
//		hmdMain.put("totalsize", growthIteration);

		
		List<PBase> population = hmdMain.getList(VConstants.population);
		if (population == null) {
			return;
		}
		for (PBase p : population) {
			int size = p.getInt(VConstants.size);
			PBase techStats = p.getType(VConstants.technology);
			int growthIteration = techStats.getInt(VConstants.growth)+ 20;
			p.put("totalsize", growthIteration);

			if (size > growthIteration) {
				PBase p2 = p.clone();
				int hsize = size / 2;
				randomize(p2);
				p2.put(VConstants.size, hsize);
				p.put(VConstants.size, hsize);
				population.add(p2);
				break;
			}
		}
		//
		// The split gives some random small differences between the two
		// populations

		// take the difference in culture, amount of conflict, and determine a
		// conflict (fight or flight for both sides)

		// additionally conflict can move and reform populations

		// move
		// for each if above x amounts (total pop + size) then move one

		//int tPop = getTPop(population);
		if (population.size() > 1) {
			HashMapData toMove = findNewMD(hmdMain.getParent(), hmdMain);
			PBase p = population.get(0);
			PBase p2 = population.get(1);
			
			int conflictAdded = p.getInt(VConstants.conflict);
			conflictAdded += p2.getInt(VConstants.conflict);
			
			if (VConstants.getRandom().nextDouble() > .3+.1 * conflictAdded ) {
				// fighting
				int amount=(int) (p.getInt(VConstants.size) * Math.min(.9,(VConstants.getRandom().nextDouble()+.3)));
				p.put(VConstants.size, p.getInt(VConstants.size) - amount -p2.getInt(VConstants.strength));
				p2.put(VConstants.size, p2.getInt(VConstants.size) - amount- p.getInt(VConstants.strength));
				TechnologyRule.transferTechnology(p,p2);
				if(p.getInt(VConstants.size) <= 0){
					population.remove(p);
				}
				if(p2.getInt(VConstants.size) <= 0){
					population.remove(p2);
				}
				hmdMain.put(new VisualDamage("sword"));
			}
			//cityRule.execute(map);
			PBase ptm = VConstants.getRandomFromList(population);
			if (ptm != null&&toMove != null && !toMove.containsKey(VConstants.obstacle)) {
				population.remove(ptm);
				PBase technology=ptm.getType("currenttech");
				String tech=VConstants.getRandomFromList(Arrays.asList(technology.getObjMap().keySet().toArray(new String[0])));
				technology.remove(tech);
				toMove.getListCreate(VConstants.population).add(ptm);
			}
		}
		// reform
		// if total size is below an amount merge together
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
}
