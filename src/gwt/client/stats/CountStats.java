package gwt.client.stats;

import gwt.client.main.IFullMapExecute;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;




public class CountStats extends Statistics {
	
	 String type;
	 int num ;
	public CountStats() {

	}
	public CountStats(PTemplate key) {
		this.key = key;
	}
	
	@Override
	public CountStats clone() {
		return new CountStats(key,type,num);
	}
	
	public CountStats(PTemplate key, String type, int num) {
		super(key);
		this.type = type;
		this.num = num;
	}
	//might eventually want to break this into two methods
	public Object checkSatisfied(Person person) {
		person.getParent().getParent().iterate(new IFullMapExecute() {
		
		@Override
		public void execute(HashMapData mapdata) {
			CountStats.this.count(mapdata);
		}
		});
		
		if(getStats(type) > num){
			return  key;
		}
		return null;
	}
	
}
