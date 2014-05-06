package gwt.client.map;

import gwt.client.astar.world.Mover;
import gwt.client.astar.world.WorldMap;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.Person;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.ImageCache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AreaMap<T extends MapData,E extends MapData> extends HMapData implements WorldMap, Iterable<T> {
	public int xdisplay = 0; 
	public int ydisplay=0;
	public int displaysize = 100;
	public ImageCache imgCache ;
	
	//public @Id Long id;
	public AreaMap() {
		
	}
	public AreaMap(int size) {
		setXsize(size);
		setYsize(size);
		setiMD(size,size);
	}
	public AreaMap(int sizex, int sizey) {
		setXsize(sizex);
		setYsize(sizey);
		setiMD(sizex,sizey);
	}
	public T getData(Point pos) {
		return getData(pos.x,pos.y);
	}
	public T getData(IPhysical pos) {
		return getData(pos.getX(),pos.getY());
	}
	@Override
	public E getParent() {
		
		return(E) super.getParent();
	}
	
	
	public void iterate(IFullMapExecute ifme) {

		for (T a : this) {
			if(a == null){
				continue;
			}
			a.iterate(ifme);

		}
	}

	// private Map<String,MapData> mapdata = new HashMap<String, MapData>();

	//@Serialized
	//MapData[][] iMD;
	

	public MapData getiMD(int x, int y) {
		List<List<MapData>> imd=(List<List<MapData>>) get(VConstants.imd);
		return imd.get(x).get(y);
	}
	public void setiMD(int xs, int ys) {
		List l = new ArrayList(xs);
		for(int a = 0; a < xs; a++){
			ArrayList arrayList = new ArrayList(ys);
			l.add(arrayList);
			for(int z = 0; z < ys; z++){
				arrayList.add(null);
			}
		}
		put(VConstants.imd,l);
	}
	public void putiMD(int x, int y,MapData o){
		List<List<MapData>> imd=(List<List<MapData>>) get(VConstants.imd);
		imd.get(x).set(y, o);
	}
	public void init() {
		
		if(getXsize() == 0){
			setXsize(1);
		}
		if(getYsize() == 0){
			setYsize(1);
		}
		setiMD(getXsize(),getYsize());

		for (int x = 0; x < getXsize(); x++) {
			for (int y = 0; y < getYsize(); y++) {

				getData(x, y);
			}
		}
		visited=new boolean[getXsize() + 1][getYsize() + 1];

	}
	public void initIfNeeded(){
		if(get(VConstants.imd) == null){
			init();
		}
		if(visited == null){
			visited=new boolean[getXsize() + 1][getYsize() + 1];
		}
	}

	public int getXsize() {
		return getInt(VConstants.xsize);
	}

	public void setXsize(int xsize) {
		put(VConstants.xsize, xsize);
	}

	public int getYsize() {
		return getInt(VConstants.ysize);
	}

	public void setYsize(int ysize) {
		put(VConstants.ysize,ysize);
	}



	public void setData(int x, int y, T data) {

		// if (mapdata.containsKey(key)) {
		//			
		// } else {
		setMapData(x, y, data);
		data.setParent(this, x, y);

		// }
	}

	public void setMapData(int x, int y, T data) {
		if(x >= getXsize() || y >=getYsize()||x < 0 || y < 0){
			return;
		}
		putiMD(x,y,data);
	}

	public void setData(Point pos, T data) {

		setData(pos.x, pos.y, data);
	}

	public T getData(int x, int y) {
	
		T hashMapData = getMapData(x, y);
		if (hashMapData == null&&!outside(x, y)) {
			hashMapData = getNewPoint();
			if(hashMapData != null){
				setData(x, y, hashMapData);
			}
			
			// mapdata.put(getKey(x, y), hashMapData);
		}
		return hashMapData;
	}

	public T getMapData(int x, int y) {
		if(x >= getXsize() || y >=getYsize()|| x < 0 || y < 0){
			return null;
		}
		return (T) getiMD(x,y);
	}

	protected T getNewPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	

	public String toString(boolean one) {
		String uexplan = "";
		String a = "";
		for (int y = 0; y < getYsize(); y++) {

			for (int x = 0; x < getXsize(); x++) {
				String value = "-      ";

				value = getStringPos(one, y, x);
				
				

				a += value+"-";

			}
			a +=  '\n';
		}

		
		return a;
	}
	protected String getStringPos(boolean one, int y, int x) {
		String value;
		MapData data = getData(x, y);
		if(data == null){
			return "none";
		}
		value = "" + data.getValue();

		

		if (StringUtils.isEmpty(data.getValue())) {
			value = "none";
		} else {
				value = data.getValue();
			


		}

		
		

		return value;
	}

	public void clearVisited() {
		for (int x = 0; x < getWidth() - 1; x++) {
			for (int y = 0; y < getHeight() - 1; y++) {
				visited[x][y] = false;
			}
		}
	}

	boolean visited[][] ;

	
	public T getNearby(IPhysical iphys, GetForNearby<T> gfn, int radius) {
		Point p= iphys.getPosition();
		if(p == null){
			return null;
		}
		int xs =p.x;
		int ys = p.y;
		T hmd= gfn.get(xs, ys);
		if(hmd != null){
			return hmd;
		}
		for (int d = 1; d < radius; d++) {
			for (int i = 0; i < d + 1; i++) {
				int x1 = xs - d + i;
				int y1 = ys - i;
				if(!outside(x1, y1)){
					hmd = gfn.get(x1, y1);
				}
				if (hmd != null) {
					return hmd;
				}

				int x2 = xs + d - i;
				int y2 = ys + i;

				if(!outside(x2, y2)){
					hmd = gfn.get(x2, y2);
				}
				if (hmd != null) {
					return hmd;
				}
			}

			for (int i = 1; i < d; i++) {
				int x1 = xs - i;
				int y1 = ys + d - i;

				// Check point (x1, y1)
				if(!outside(x1, y1)){
					hmd = gfn.get(x1, y1);
				}
				if (hmd != null) {
					return hmd;
				}
				int x2 = xs + d - i;
				int y2 = ys - i;

				// Check point (x2, y2)
				if(!outside(x2, y2)){
					hmd = gfn.get(x2, y2);
				}
				if (hmd != null) {
					return hmd;
				}
			}
		}
		return null;
	}

	public boolean outside(int x, int y) {
		if (getXsize() <= x || getYsize() <= y) {
			return true;
		}
		if (0 > x || 0 > y) {
			return true;
		}
		return false;
	}

	@Override
	public int getHeight() {
		return getYsize();
	}

	@Override
	public int getTerrain(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		return getXsize();
	}

	@Override
	public boolean isBlocked(Mover mover,int xs,int ys, int x, int y) {
		Person p = (Person) mover;
		
		return false;
	}

	@Override
	public boolean isVisited(int x, int y) {
		return visited[x][y];
	}

	@Override
	public void setVisited(int x, int y) {
		visited[x][y] = true;

	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int xcount = 0;
			int ycount = 0;

			@Override
			public boolean hasNext() {
				if(!containsKey(VConstants.imd)){
					return false;
				}
				return getYsize() > ycount;
			}

			@Override
			public T next() {
				T md= null;
				
				md=(T) getiMD(xcount,ycount);
				xcount++;
				if(xcount >= getXsize()){
					ycount++;
					xcount = 0;
				}
				if(md == null){
					md = getNewPoint();
					setData(xcount,ycount, md);
					
				}
				return md;
			}

			@Override
			public void remove() {
				throw new IllegalArgumentException();
			}
		};
	}

	public void setSize(int size) {
		setXsize(size);
		setYsize(size);
	}

	
//	public String toStringChoice(List<Point> choices) {
//		
//		String a = "";
//		for (int y = 0; y < ysize; y++) {
//
//			for (int x = 0; x < xsize; x++) {
//				String value = " ";
//				for(Point p : choices){
//					if(p.x == x && p.y == y){
//						value = getStringPos(false, y, x);
//						break;
//					}
//					
//				}
//				
//				
//				
//
//				a += value+"-";
//
//			}
//			a += '-';
//		}
//
//		
//		return a;
//	}
	
	public Point getEdge(Direction dir) {
		int x = getXsize() / 2;
		int y = getYsize() / 2;
		if(dir.x > 0){
			x = getXsize() -1;
		}
		if(dir.x < 0){
			x = 0;
		}
		
		if(dir.y > 0){
			y = 0;
		}
		if(dir.y < 0){
			y = getYsize() -1;
		}
		Point p = new Point(x,y);
		return p;
	}
	
	
	
	public ImageCache getImgCache() {
		if(imgCache == null){
			imgCache  =new ImageCache();
		}
		return imgCache;
	}
	@Override
	public AreaMap clone() {
		
		return new AreaMap().copyProperties(this);
	}
	
}
