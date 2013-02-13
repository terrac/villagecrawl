package gwt.client.map;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.Point;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.googlecode.objectify.annotation.Serialized;


public class HMapData extends MapData implements IPhysical{


	
	
	public Point getPosition() {
		AreaMap fmd = (AreaMap) getParent();
		for (int x = 0; x < fmd.getXsize(); x++) {
			for (int y = 0; y < fmd.getYsize(); y++) {

				if(this.equals(fmd.getData(x, y))){
					return new Point(x,y); 
				}
			}
		}
		return null;
	}
	
	public void putEvenIfNotEmpty(String key,MapData value){
		
		if(value != null&&value.getParent() == null){
			value.setParent(this);
		}
		
		//getMap().put("value",new StringMapData( value));
		getObjMap().put(key, value);
	}
	public boolean put(String key,MapData value){
		//Log.log("abc", key+" "+value);
		if(containsKey(key)){
			return false;
		}
		putEvenIfNotEmpty(key, value);
		return true;
	}
	public boolean put(MapData md){
		return put(md.getKey(),md);
	}
	
	
		
	
	public String getValue(){
		return ""+getObjMap();
	}


	public Object remove(String key){
		
		Object mapData =  get(key);
		super.remove(key);
		
		
		if(mapData == null){
			return null;
		}
		if(mapData instanceof MapData){
			((MapData) mapData).setParent(null);	
		}
		
		return mapData;
	}
	
	public void remove(MapData md){
		remove(md.getKey());
	}


	

	
	public boolean containsValue(String value){
		return getObjMap().containsValue(value);
	}
	
	
	public MapData getAndRemove(String key){
		MapData mapData =  getMapData(key);
		if(mapData != null){
			remove(key);
		}
		return mapData;
	}
	
	

	public Collection values(){
		
		return getObjMap().values();
	}
	
	public Collection<MapData> removableValues(){
		
		List<MapData> mdlist = new ArrayList<MapData>();
		for(Object obj : getObjMap().values()){
			if(obj instanceof MapData){
				mdlist.add((MapData) obj);
			}
			
		}
		
		
		return mdlist;
	}
	public Collection<String> keySet(){
		
		
		return getObjMap().keySet();
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return ""+map;
//	}
	@Override
	public String toString() {

		//return getObjMap().toString();
		String b = "[";
		for(Entry<String,Object> en : getObjMap().entrySet()){
			Object value2 = en.getValue();
			if(!(value2 instanceof MapData)){
				b += "\n\n"+en.getKey()+" "+value2;	
				continue;
			}
			MapData value = (MapData) value2;
			b += "\n\n"+en.getKey();
			if(value == null){
				continue;
			}
			b +="="+value.toString()+",";
		}
		b = b.substring(0,b.length()-1);
		b += "]";
		return b;
	}

	
	
	
//	public List<MapData> getDiffs(HMapData data2) {
//		List<MapData> mdlist = new ArrayList<MapData>();
//		for(MapData md : values()){
//			if(!data2.containsKey(md.getKey())){
//				mdlist.add(md);
//			}
//		}
//		return mdlist;
//	}
	public void clear() {
		getObjMap().clear();
		
	}
	public int size(){
		return getObjMap().size();
	}	
	
//	@Override
//	public MapData get(String key) {
//		// TODO Auto-generated method stub
//		return (MapData) super.get(key);
//	}
	public MapData getMapData(String key){
		Object object = super.get(key);
		if(object instanceof MapData){
			return (MapData) object;
		}
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return getPosition().getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return getPosition().getY();
	}

	@Override
	public void setX(int x) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setY(int y) {
		throw new IllegalArgumentException();
		
	}
}
