package gwt.client.map;

import gwt.client.EntryPoint;
import gwt.client.astar.world.Mover;
import gwt.client.game.util.PointBase;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.MapArea;
import gwt.client.main.base.PBase;
import gwt.client.map.runners.GetForNearby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

public class SymbolicMap extends AreaMap<FullMapData, MapArea> {

	public SymbolicMap() {

	}

	// @Override
	// protected void init() {
	// iMD = new MapData[xsize][ysize];
	// }
	// {
	// setXsize( 1);
	// setYsize( 1);
	// //init();
	// }
	@Override
	public MapArea getParent() {
		// TODO Auto-generated method stub
		return (MapArea) super.getParent();
	}

	public SymbolicMap(MapArea mapArea) {
		setParent(mapArea);
	}

	public SymbolicMap(MapArea mapArea, int sizex, int sizey) {
		setXsize(sizex);
		setYsize(sizey);
		setParent(mapArea);
		setiMD(getXsize(), getYsize());
	}

	@Override
	protected FullMapData getNewPoint() {

		return new FullMapData(this, (FullMapData) get(VConstants.template));
	}

	class getempty extends GetForNearby {

		@Override
		public AreaMap get(int x1, int y1) {
			AreaMap hmd = null;
			if (outside(x1, y1)) {
				return null;
			}
			if (!getData(x1, y1).containsKey("builtup")) {
				hmd = getData(x1, y1);
			}
			return hmd;
		}
	}

	// List<MapData> fmdlist = new ArrayList<MapData>();
	// @PrePersist
	// public void save(){
	// fmdlist.addAll(values());
	// iMD = null;
	// }
	// @PostLoad
	// public void load(){
	// int xcount = 0;
	// int ycount = 0;
	// for(MapData md : fmdlist){
	// xcount++;
	// if(xcount >= xsize){
	// ycount++;
	// xcount = 0;
	// }
	// iMD[xcount][ycount] = md;
	// }
	// fmdlist = null;
	// }

	// public FullMapData getNearestEmpty(int xs,int ys) {
	// GetForNearby gfn = new getempty();
	// return getNearby(xs, ys, gfn,20);
	// }

	@Override
	public boolean isBlocked(Mover mover, int xs, int ys, int x, int y) {
		HashMapData hmd = null;
		FullMapData from = getData(xs, ys);

		List<Point> portalList = from.getType(VConstants.cache).getList(
				VConstants.portal);
		if (portalList == null) {
			portalList = new ArrayList<Point>();
			for (HashMapData fhmd : from) {
				PBase pb = fhmd.getPBase(VConstants.portal);
				if (pb != null && pb instanceof PointBase) {
					portalList.add(Point.getPoint((PointBase) pb));
				}
			}
			from.getType(VConstants.cache).put(VConstants.portal, portalList);
		}
		return !portalList.contains(new Point(x, y));

	}

	public FullMapData getAssociatedFMD(String type){
		for(FullMapData fmd : this){
			if(fmd.getList(VConstants.type).contains(getS(VConstants.type))){
				continue;
			}
			return fmd;	
		}
		return null;
	}
	
	@Override
	public AreaMap clone() {

		return new SymbolicMap().copyProperties(this);
	}

}
