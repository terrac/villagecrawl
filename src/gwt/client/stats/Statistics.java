package gwt.client.stats;

import gwt.client.main.VConstants;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

import java.util.HashMap;
import java.util.Map;


public class Statistics extends MapData{
	Map<String,Integer> map = new HashMap<String, Integer>();
	public Statistics(PTemplate key) {
		this.key = key;
	}
	public Statistics() {

	}
	public void count(HMapData mapdata) {
		MapData mapData2 = mapdata.getMapData(VConstants.gate);
		if(mapData2 == null){
			return;
		}
		String name=mapData2.getValue();
		if(!map.containsKey(name)){
			map.put(name, 0);
		}
		map.put(name,map.get(name)+1);
	}
	public void setKey(PTemplate key) {
		this.key = key;
	}
	@Override
	public String getKey() {
		return "statistics";
	}

	
	
	public int getStats(String key){
		Integer inte=map.get(key);
		if(inte == null) return 0;
		return inte;
	}
	@Override
	public Statistics clone() {
		return new Statistics(key);
	}
	PTemplate key;
	
	//might eventually want to break this into two methods
	public Object checkSatisfied(LivingBeing person) {
		person.getParent().getParent().iterate(new IFullMapExecute() {
		
		@Override
		public void execute(HashMapData mapdata) {
			Statistics.this.count(mapdata);
		}
		});
		
		if(getStats("rock") > 0){
			return  key;
		}
		return null;
	}
	
}
