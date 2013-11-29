package gwt.client.map;

import gwt.client.EntryPoint;
import gwt.client.astar.world.Mover;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.Person;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.Soil;
import gwt.client.map.getters.FGetList;
import gwt.client.map.getters.KeyValue;
import gwt.client.map.runners.BaseSetFull;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.IClientObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Serialized;

public class FullMapData extends AreaMap<HashMapData, SymbolicMap> implements
		IClientObject {

	
	public static final String fullMapData = "fullmapdata";
	public @Id
	Long id;

	// {
	// setXsize(17);
	// setYsize( 15);
	// //init();
	// }

	public FullMapData(String value) {
		super();
	}

	public FullMapData() {

	}

	public FullMapData(int xs, int ys) {
		super();
		setXsize(xs);
		setYsize(ys);
		init();
	}

	public FullMapData(SymbolicMap mapArea, FullMapData fmd) {
		setXsize(mapArea.getInt(VConstants.xfull));
		setYsize(mapArea.getInt(VConstants.yfull));

		setParent(mapArea);
		if (fmd != null) {
			this.copyDeepProperties(fmd);
			//setFullMapData(fmd, 0, 0, null);
		}
	}

	public String toString() {
		// if(getValue() == null){
		return super.toString();
		// }
		// return getValue();
	}

	public void setFullMapData(FullMapData fmd, int x, int y, LivingBeing person) {
		new setfull(person).runSetup(this, fmd, x, y);
	}

	// public List<MapData> getDiffs(DiffFullMapData currentData) {
	// List<MapData> diffs = new ArrayList<MapData>();
	// for (int a = 0; a < getXsize(); a++) {
	// for (int b = 0; b < getYsize(); b++) {
	// HashMapData data = getData(a, b);
	// HMapData data2 = currentData.getData(a, b);
	//						
	//						
	//						
	//						
	// List<MapData> diffs2 = data.getDiffs( data2);
	// for(MapData md :diffs2){
	// data2.put(md);
	// }
	// diffs.addAll(diffs2);
	//												
	//
	// }
	//
	// }
	//			
	// return diffs;
	// }

//	@Override
//	public String getValue() {
//	}
//	@Override
//	public String getImage() {
//		if (keyToString == null) {
//			return null;
//		}
//		if (people.size() == 0) {
//			return null;
//		}
//		return keyToString.getImage();
//	
//	}
	@Override
	public String getValue() {
		
		return null;
	}
	public HashMapData getNear(String type, IPhysical py, int radius) {
		GetForNearby gfn = new gettype(type);
		return getNearby(py, gfn, radius);
	}

	public HashMapData getNearKeyValueUnoccupied(String key, String value,
			IPhysical py, int radius) {
		GetForNearby gfn = new getkeyvalueunoccupied(key, value);
		return getNearby(py, gfn, radius);
	}

	public HashMapData getNearKeyValue(String key, Object value, IPhysical py,
			int radius) {
		GetForNearby gfn = new getkeyvalue(key, value);
		return getNearby(py, gfn, radius);
	}
	
	public HashMapData getAllKeyValue(String key, Object value) {
		GetForNearby gfn = new getkeyvalue(key, value);
		for(HashMapData hmd : this){
			HashMapData h=(HashMapData) gfn.get(hmd);
			if(h != null){
				return h;
			}
		}
		return null;
	}
	
	public HashMapData getNearKeyValueNot(String key, String value, IPhysical py,
			int radius) {
		getkeyvalue gfn = new getkeyvalue(key, value);
		gfn.not = true;
		return getNearby(py, gfn, radius);
	}

	public HashMapData getNearestEmpty(Point point) {
		GetForNearby gfn = new getempty();
		return getNearby(point, gfn, 20);
	}

	public HashMapData getNearestEmpty(IPhysical py) {
		GetForNearby gfn = new getempty();
		return getNearby(py, gfn, 20);
	}

	public HashMapData getNearestEmptyNotEvent(IPhysical py) {
		getempty gfn = new getempty();
		gfn.containsEvent = false;
		return getNearby(py, gfn, 20);
	}

//	public HashMapData getNearestEmptyAndUnbuilt(IPhysical py, int rad) {
//		GetForNearby gfn = new getemptyandunbuilt();
//		return getNearby(py, gfn, rad);
//	}

	class getempty extends GetForNearby {
		boolean containsEvent = true;

		@Override
		public HashMapData get(int x1, int y1) {
			HashMapData hmd = null;
			if (outside(x1, y1)) {
				return null;
			}
			
			if (!isBlocked(null,0,0, x1, y1)) {
				hmd = getData(x1, y1);
			}
			if (hmd!= null&&!containsEvent&&hmd.containsKey(AttachUtil.personadded)) {
				return null;
			}
			return hmd;
		}
	}

	class gettype extends GetForNearby {
		String type;

		@Override
		public HashMapData get(int x1, int y1) {
			HashMapData hmd = null;
			if (outside(x1, y1)) {
				return null;
			}
			if (getData(x1, y1).containsKey(type)) {
				hmd = getData(x1, y1);
			}
			return hmd;
		}

		public gettype(String type) {
			super();
			this.type = type;
		}
	}

	class getkeyvalueunoccupied extends GetForNearby {
		String key;
		String value;

		public getkeyvalueunoccupied(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public HashMapData get(int x1, int y1) {
			HashMapData hmd = null;

			HashMapData chmd = getData(x1, y1);
			if (chmd == null||chmd.containsKey(VConstants.livingbeing)) {
				return null;
			}
			MapData md = chmd.getMapData(key);
			if (md == null&&chmd.getItems() != null) {
				md = chmd.getItems().getItem(key);
			}
			// if we only want the key checked
			if (md != null && value == null) {
				return chmd;
			}
			if (md != null && value.equals(md.getValue())) {
				hmd = chmd;

			}
			//			
			return hmd;
		}

	}

	class getkeyvalue extends GetForNearby {
		String key;
		Object value;
		String[] keys;

		public getkeyvalue(String key, Object value) {
			super();
			if (key.contains(".")) {
				keys = key.split("\\.");
				
			} else {
				this.key = key;
			}

			this.value = value;
		}
		boolean not;
		@Override
		public HashMapData get(int x1, int y1) {
			

			HashMapData chmd = getData(x1, y1);
			return (HashMapData) get(chmd);
					}
		@Override
		public MapData get(MapData chmd) {
			Object md = null;
			if (keys != null) {
				Object pb = chmd;
				for (String k : keys) {
					if (pb == null||!(pb instanceof PBase)) {
						break;
					} else {
						pb = ((PBase) pb).get(k);
					}

				}
				md = pb;
			} else {
				md = chmd.get(key);
			}

			// if (md == null) {
			// md = chmd.getItems().get(key);
			// }

			// if we only want the key checked
			if (md != null && value == null) {
				return chmd;
			}
			if(md != null){
				if (md instanceof MapData){
					if(not^value.equals(((MapData) md).getValue())){
						return chmd;
					}
				}
				if(not^value.equals(md)){
					return chmd;
				}
			}

			
			
			return null;

		}
	}

	public class setfull extends BaseSetFull {
		public setfull(LivingBeing person) {
			this.person = person;
		}

	}

	// public void refresh(){
	// for(Entry<String, HashMapData> hhh:getHashMapData().entrySet()){
	// break;
	// }
	// }

	// public Point getClosestWithinOne(HashMapData hstart, String
	// keyWithin,String keyToGet){
	// Set<Point>hits = new HashSet();
	// Set<Point> points = new HashSet();
	// Queue<Point> queue = new LinkedList();
	// Point start = hstart.getPosition();
	// queue.add(start);
	// while(!queue.isEmpty()){
	// Point cpoint=queue.poll();
	// HashMapData hmd = getData(cpoint);
	// if(hmd.containsKey(keyToGet)){
	// hits.add(cpoint);
	// }
	// if(!hmd.containsKey(keyWithin)){
	// continue;
	// }
	// if(points.contains(cpoint)){
	// continue;
	// } else {
	// points.add(cpoint);
	// }
	//				
	//			
	// queue.add(new Point(cpoint.x+1,cpoint.y));
	// queue.add(new Point(cpoint.x-1,cpoint.y));
	// queue.add(new Point(cpoint.x,cpoint.y+1));
	// queue.add(new Point(cpoint.x,cpoint.y-1));
	//				
	// }
	// //this is only really useful for small sizes
	// double lowest = 9999;
	// Point lowestP = null;
	// for(Point hmd : hits){
	// double len=Point.distance(start.x, start.y, hmd.x, hmd.y);
	// if(len < lowest){
	// lowest = len;
	// lowestP = hmd;
	// }
	// }
	// return lowestP;
	//			
	// }
	@Serialized
	MapData keyToString;

	public boolean add(Direction dir, MapData md) {
		if (dir == null) {
			return false;
		}
		// stub
		keyToString = md;
		Point p = getEdge(dir);

		HashMapData data = getData(p.x, p.y);
		if (data.containsKey(VConstants.livingbeing)) {
			data = getNearestEmpty(data);
		}
		if(data == null){
			return false;
		}
		boolean put;
		if (md instanceof LivingBeing) {
			if (md.getParent() != null && md.getParent().getParent() != null) {
				((FullMapData) md.getParent().getParent()).people.remove(md);
			}
			put = data.put(md.getKey(), md);
			addPerson((LivingBeing) md);
		} else {
			put = data.put(md.getKey(), md);
		}

		return put;

	}

	public Point getNextDirectionPoint(AreaMap to) {
		
		
		Point nTo = new Point();
		nTo.x = getXsize() / 2;
		nTo.y = getYsize() / 2;
		Point toP = to.getPosition();
		Point fromP = this.getPosition();
		if(fromP ==null){
			return null;
		}
		int x = toP.x - fromP.x;
		int y = toP.y - fromP.y;
		if (x > 0) {
			nTo.x = getXsize() - 1;
		}
		if (x < 0) {
			nTo.x = 0;
		}

		if (y > 0) {
			nTo.y = getYsize() - 1;
		}
		if (y < 0) {
			nTo.y = 0;
		}

		return nTo;
	}

	public boolean moveNextAvailableFullMapDataStartHashMap(LivingBeing md) {
		Point hmdP = md.getPosition();
		Point fmdP = this.getPosition();

		int x = 0;
		int y = 0;
		if (hmdP.x == 0) {
			x = x - 1;

		}
		if (hmdP.x == getXsize() - 1) {
			x = x + 1;
		}
		if (hmdP.y == 0) {
			y -= 1;
		}
		if (hmdP.y == getYsize() - 1) {
			y += 1;
		}
		if (getParent().outside(fmdP.x + x, fmdP.y + y)) {
			return false;
		}
		return this.getParent().getData(fmdP.x + x, fmdP.y + y).add(
				Direction.get(-x, y), md);

		// return false;
	}

	public void addPerson(LivingBeing md) {
		if (md == null) {
			return;
		}
		if (md.getParent() != null) {
			md.getParent().getParent().people.remove(md);
		}
		boolean b = people.size() == 0;

		people.add(md);

		if (b) {
			//EntryPoint.game.getHtmlOut().currentFMD = this;
			startOnMap(md);

		}
		// AttachUtil.run(AttachUtil.personadded, md, this.getParent());
		AttachUtil.run(AttachUtil.personadded, md, EntryPoint.game);
		AttachUtil.run(AttachUtil.personlist, md, EntryPoint.game);
	}

	public void startOnMap(LivingBeing md) {
		if (!getB("started")) {
			//EntryPoint.game.getHtmlOut().currentFMD = this;
			put("started", true);
			AttachUtil.run(AttachUtil.personStartOnMap, md, this);
			AttachUtil.run(AttachUtil.personStartOnMap, md, EntryPoint.game);
		}
	}

	@Override
	public boolean isBlocked(Mover mover,int xs,int ys, int x, int y) {

		HashMapData data = getData(x, y);
		return data.isBlock();
	}

	@Override
	public Iterator<HashMapData> iterator() {

		return super.iterator();
	}

	@Override
	protected HashMapData getNewPoint() {
		HashMapData hmd = new HashMapData();

		return hmd;
	}

	public List<LivingBeing> people = new ArrayList();

	public boolean removePerson(LivingBeing lb) {
		AttachUtil.run(AttachUtil.personlist, lb, EntryPoint.game);
		return people.remove(lb);
	}

	public List<LivingBeing> getLivingBeings() {
		return people;
	}

	public List<Person> getPeople() {
		List<Person> p = new ArrayList();
		for (LivingBeing person : people) {
			if (person instanceof Person) {
				p.add((Person) person);
			}
		}
		return p;
	}

	@Override
	public AreaMap clone() {

		return new FullMapData().copyDeepProperties(this);
	}

	@Override
	public Point getPosition() {
		SymbolicMap fmd = getParent();
		for (int x = 0; x < fmd.getXsize(); x++) {
			for (int y = 0; y < fmd.getYsize(); y++) {

				if (this.equals(fmd.getData(x, y))) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}

	public HashMapData getNearO(IPhysical person, int range, KeyValue ...keyValues ) {
		
		return getNearby(person,new FGetList(keyValues,this),range);
	}

	public void addType(String value) {
		List<String> l = getListCreate(VConstants.type);
		if(!l.contains(value)){
			l.add(value);
		}
	}

	public static void addRandomSpot(FullMapData fmd,MapData item) {
		int xSize = VConstants.getRandom().nextInt(fmd.getXsize());
		int ySize = VConstants.getRandom().nextInt(fmd.getYsize());
		fmd.getNearestEmpty(new Point(xSize, ySize)).putAppropriate(item);
	}

	public HashMapData getNearestPerson(final IPhysical lb,int radius) {
		return getNearby(lb, new GetForNearby<HashMapData>(this) {
			@Override
			public HashMapData get(HashMapData hashmapdata) {
				if(hashmapdata.getPosition().equals(lb.getPosition())){
					return null;
				}
				if(hashmapdata.getLivingBeing() != null){
					return hashmapdata;
				}
				return null;
			}
		}, radius);
	}
	
	
}
