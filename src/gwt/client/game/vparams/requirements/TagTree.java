package gwt.client.game.vparams.requirements;

import java.util.Map;

import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class TagTree extends VParams {
	
	@Override
	public void execute(Map<String, Object> map) {
		//takes the specific tags and adds on any tags
		
		//also adds the generic tag regardless
	}
	@Override	
	public PBase clone() {
		
		return new TagTree().copyProperties(this);
	}
	
}
