package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class BuildMap extends VParams {

	public BuildMap(PBase charmap, String map) {
		put(VConstants.pbase, charmap);
		put(VConstants.map, map);
	}
	public BuildMap(PBase charmap, String map,String type) {
		this(charmap, map);
		getType(VConstants.resource).getListCreate(VConstants.type).add(type);

	}
	
	public BuildMap(PBase charmap, String map, String unbuilt,String type) {
		this(charmap, map);
		put(VConstants.unbuilt, unbuilt);
		getType(VConstants.resource).getListCreate(VConstants.type).add(type);
	}

	public BuildMap() {

	}

	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = (FullMapData) map.get(AttachUtil.OBJECT);
		
		if (containsKey(VConstants.resource)) {
			for (Entry<String, Object> o : getPBase(VConstants.resource)
					.getObjMap().entrySet()) {
				fmd.put(o.getKey(), o.getValue());
			}
		}
		if (containsKey(VConstants.overmap)) {
			fmd.put(VConstants.overmap, get(VConstants.overmap));
		}
		if (containsKey(VConstants.defaultimage)) {
			fmd.put(VConstants.defaultimage, get(VConstants.defaultimage));
		}
		PBase pb = getPBase(VConstants.pbase);
		String smap = getS(VConstants.map);
		String[] sarr = smap.split("\n");
		fmd.setYsize(sarr.length);
		fmd.setXsize(sarr[0].length());
		fmd.init();
		int y = 0;
		for (String s : sarr) {
			int x = 0;
			for (char c : s.toCharArray()) {

				Object md = pb.get("" + c);

				if (md instanceof List) {
					for (Object m : (List) md) {
						HashMapData data = fmd.getData(x, y);
						putData(data, md);
					}
				} else {
					HashMapData data = fmd.getData(x, y);
					String unbuilt = getS(VConstants.unbuilt);
					if (data != null) {
						if (unbuilt != null) {
							data.put(new SimpleMD(VConstants.gate, unbuilt));
							if(md == null){
								md = VConstants.none;
							}
							data.getPBase(VConstants.gate).put(
									VConstants.unbuilt, md);
						} else if(md != null) {
							putData(data, md);
						}
					}

				}

				x++;
			}

			y++;
		}
		AttachUtil.run(VConstants.list, fmd, this);

	}

	public static void putData(HashMapData hmd, Object md) {
		MapData mapdata = null;
		PBase pBase = EntryPoint.game.getPBase(VConstants.stored);
		if (!(md instanceof String) && md instanceof MapData) {
			mapdata = (MapData) md;
			mapdata = mapdata.clone();
		} else if (md instanceof String) {
			String name = (String) md;
			if (pBase != null) {

				mapdata = (MapData) pBase.get(name);

				// if (mapdata == null) {
				// String key = getS(VConstants.key);
				// if (key != null) {
				// String value = getS(VConstants.value);
				// name = name.replace(key, value);
				// mapdata = (MapData) pBase.get(name);
				// }
				//
				// }
				if (mapdata != null) {
					mapdata = mapdata.clone();
					// might add a "dontclone flag
				}
			}
			if (mapdata == null) {
				if (name.startsWith(VConstants.livingbeing)) {
					mapdata = RandomPersonCreation.createPerson(name);
				}

			}

		}

		if (md instanceof PBase) {
			PBase pb = (PBase) md;
			if (VConstants.livingbeing.equals(pb.getS(VConstants.type))) {
				mapdata = RandomPersonCreation.createPerson(pb);
			}
		}

		if (mapdata != null && mapdata.containsKey(VConstants.categories)) {
			List<String> cat = mapdata.getListCreate(VConstants.categories);
			mapdata = ((RandomCreation) EntryPoint.game.getPBase(
					VConstants.categories).getPBase(mapdata.getKey()))
					.randomize(cat);
		}

		if (VConstants.exit.equals(md)) {
			mapdata = new SimpleMD(VConstants.gate, "exit", new ExitTile(
					EntryPoint.game.getPBase(VConstants.human).getS(
							VConstants.previous)));
			mapdata = new SimpleMD(VConstants.gate, "exit", new ExitTile(
					EntryPoint.game.getPBase(VConstants.human).getS(
							VConstants.previous)));
		}

		if (mapdata == null) {
			mapdata = new SimpleMD(VConstants.obstacle, (String) md);
		}

		if (hmd != null) {
			hmd.putAppropriate(mapdata);
		}
	}

	@Override
	public PBase clone() {
		return new BuildMap().copyDeepProperties(this);
	}

}
