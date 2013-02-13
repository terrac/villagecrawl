package gwt.client.stats;

import gwt.client.main.VConstants;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BStatistics extends MapData{
	int numberFood;
	int foodHealth;
	public List<String> foodlist;
	public BStatistics() {

	}
	public void count(HMapData mapdata) {
		Plant mapData2 = (Plant) mapdata.get(VConstants.foodgroup);
		if(mapData2 == null){
			return;
		}
		String name=mapData2.type;
		if(foodlist.contains(name)){
			numberFood++;
			foodHealth += mapData2.getHealth();
		}
		
	}
	
	@Override
	public String getKey() {
		return "statistics";
	}

	
	
	
	
	//might eventually want to break this into two methods
	public boolean checkSatisfied(FullMapData  fmd) {
		fmd.iterate(new IFullMapExecute() {
		
		@Override
		public void execute(HashMapData mapdata) {
			BStatistics.this.count(mapdata);
		}
		});
		if(numberFood == 0){
			return false;
		}
		foodHealth =foodHealth/numberFood;
		
		if(foodHealth > 50&&numberFood > 15){
			return true;
		}
		
		return false;
	}
	
	
	
}
