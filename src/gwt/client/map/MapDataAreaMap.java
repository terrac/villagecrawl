package gwt.client.map;

public class MapDataAreaMap extends AreaMap<MapData, MapData> {

	public MapDataAreaMap() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setData(int x, int y, MapData data) {
		//don't set parent
		setMapData(x, y, data);
	}
	public MapDataAreaMap(int sizex, int sizey) {
		super(sizex, sizey);
	}
	
	@Override
	public AreaMap clone() {
		
		return new MapDataAreaMap().copyProperties(this);
	}
}
