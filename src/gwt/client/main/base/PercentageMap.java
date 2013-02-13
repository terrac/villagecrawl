package gwt.client.main.base;

import gwt.client.main.VConstants;
import gwt.client.map.MapData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentageMap extends PBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PercentageMap() {

	}
	
	public PercentageMap(Object ...objects) {
		PBase pb = new PBase();
		put(VConstants.pbase,pb);
		int a = 0;
		while(a+1 < objects.length){
			String[] split = ((String)objects[a]).split(",");
			for(String b : split){
				pb.put(b.trim(),(Integer) objects[a+1]);
			}
			
			a +=2;
		}
	}

	public String runCombinedPercentages(PercentageMap seasonM) {
		Map<String,Integer> map = (Map<String, Integer>) getPBase(VConstants.pbase);
		for(String a : map.keySet()){
			Integer b=null;
			if(seasonM != null){
				b=(Integer) seasonM.getPBase(VConstants.pbase).get(a);
			}
			if(b == null){
				b = map.get(a);
			}
			if(b >VConstants.getRandom().nextInt(100)){
				return a;
			}
		}
		//do combined and then return a list of strings that will be fed to an item map;
		return null;
	}
	
	
}
