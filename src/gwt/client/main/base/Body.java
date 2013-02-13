package gwt.client.main.base;

import gwt.client.item.Item;
import gwt.client.main.Pregnancy;
import gwt.client.main.VConstants;
import gwt.client.map.AgeData;
import gwt.client.map.AgeMapData;
import gwt.client.map.HMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.PHMapData;
import gwt.client.personality.Stats;

public class Body extends PHMapData {
	public Body() {
	}
	
	Stats stats;
	public Items getItems(){
		
		Items items = (Items) get(VConstants.items);
		if(items == null){
			items = new Items();
			put(items);
		}
		return items;
	}
	
	public Body(Stats stats) {
		super();
		this.stats = stats;
	
	}
	
	public Stats getStats() {
		return stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public boolean age() {
		for(Object md :values()){
			if(md instanceof AgeMapData){
				((AgeMapData)md).age();
			}
			
		}
//		stats.setHungry(stats.getHungry()-1);
//		if(stats.getHungry() < 0){
//			return true;
//		}
		return ageDays();
	}
	protected boolean ageDays() {
		setAge(getAge()+1);
	
		
		
				
		if(getAge() >10 * 356){
			return true;
		}
		return false;
	}
	
	
}
