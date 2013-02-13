package gwt.client.stats;

import gwt.client.main.IFullMapExecute;
import gwt.client.map.HashMapData;

public class CountValue implements IFullMapExecute{
		String type;
		public CountValue(String type) {
			super();
			this.type = type;
		}
		public int count = 0;
		@Override
		public void execute(HashMapData mapdata) {
			if(type.equals(mapdata.getValue())){
				count++;
			}
			
		}
	

}
