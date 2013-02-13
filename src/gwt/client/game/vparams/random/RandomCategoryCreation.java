package gwt.client.game.vparams.random;

import java.util.List;
import java.util.Map;

import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class RandomCategoryCreation extends VParams implements RandomCreation{

	@Override
	public void execute(Map<String, Object> map) {
		// TODO Auto-generated method stub
		super.execute(map);
	}

	@Override
	public MapData randomize(List<String> categories) {
		//if a category of friendly, get the current team 1 on the fmd and gather types
		//if any of them conflict then friendly will not occur (like undead vs white magic)
		
		//types are in a heirarchy.  a humanoid would have talk, bandit, religious, etc, but these  could also
		//be directly applied.  so if you ask for merchant only merchants will be there, but if you ask for humanoid then any humanoid could be there
		
		//might just override the pbase getter to do logic
		
		return null;
	}
	
	
}
