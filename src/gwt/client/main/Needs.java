package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.map.MapData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Needs extends MapData{
	
	public Needs() {
	
	}
	@Override
	public String getKey() {
		
		return VConstants.needs;
	} 
	
	public interface Formula extends Serializable{
		public Integer getNumberNeeded(Item item);
		
		public Integer getNumberNotNeeded(Item item);
		
	}
	
	public static class FloorFormula implements Formula{
		Integer floor;
		@Override
		public Integer getNumberNeeded(Item item) {
			
			return Math.min(item.getAmount(),floor);
		}
		@Override
		public Integer getNumberNotNeeded(Item item) {
			
			return item.getAmount()-floor;
		}
		public FloorFormula(Integer floor) {
			super();
			this.floor = floor;
		}
		
		public FloorFormula() {
		
		}
	}
	List<String> propertyList = new ArrayList<String>();
	public int getAmountNotNeeded(Item item, int i) {
		for(String t : propertyList){
			if(item.containsKey(t)){
				Formula f=(Formula) get(t);
				return f.getNumberNotNeeded(item);
			}
				
			
		}
		Formula def=(Formula) get(VConstants.defaultFormula);
		return def.getNumberNotNeeded(item);
		
	}
	public int getAmountNeeded(Item item, int i) {
		for(String t : propertyList){
			if(item.containsKey(t)){
				Formula f=(Formula) get(t);
				return f.getNumberNeeded(item);
			}
				
			
		}
		Formula def=(Formula) get(VConstants.defaultFormula);
		return def.getNumberNeeded(item);
		
	}
	
	
		
	

}
