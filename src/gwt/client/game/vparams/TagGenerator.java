package gwt.client.game.vparams;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TagGenerator extends VParams {

	public TagGenerator() {

	}


	public TagGenerator(VParams ... vParams) {
		put(VConstants.list,Arrays.asList(vParams));
	}


	
	public void get(Map<String, Object> map) {
		
				
		//then decide on the saying associated with the tag
		
		//pull associated sayings based on the tag
				
		//if high haggle and low percentage use a false haggle
	
	//	map.put(VConstants., value)
	}

	@Override
	public PBase clone() {

		return new TagGenerator().copyProperties(this);
	}


	public static void add(Map<String, Object> map, String tag) {
		Map m=(Map) map.get(VConstants.taggenerator);
		Integer count = (Integer) m.get(tag);
		if(count == null){
			count = 0;
		}
		count++;
		m.put(tag, count);
	}

}
