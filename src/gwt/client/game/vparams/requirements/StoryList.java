package gwt.client.game.vparams.requirements;

import java.util.Arrays;
import java.util.Map;

import gwt.client.game.vparams.TagGenerator;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class StoryList extends VParams {
	
	public StoryList(String... events) {
		put(VConstants.list,Arrays.asList(events));
	}
	int count = 0;
	@Override
	public void execute(Map<String, Object> map) {
		// so for example you might have a leader deposed
		
		//that would go to leader deposed tag
		
		//which would do the generic leader deposed statements
		//and any ones specific to that leader
		
		String tag = (String) getList(VConstants.list).get(count);
		//take the second part of the tag and 
		//put it against the person.  The person has a specific pbase 
		//that contains all the values
		TagGenerator.add(map,tag);
		
	}
	@Override	
	public PBase clone() {
		
		return new StoryList().copyProperties(this);
	}
	
}
