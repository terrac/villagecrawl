package gwt.client.main;

import java.util.Arrays;
import java.util.List;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.PhysicalMapData;
import gwt.client.stats.BStatistics;

public class DeerPack extends OObject {

	public DeerPack() {
	
	}
	public static final String countStart = "deerpackcountstart";

	public static final String countMax = "deerpackcountmax";

	List<String> foodlist;
	
	BStatistics bs = new BStatistics();
	
	int count = 0;
	public DeerPack(String[] string) {
		foodlist = Arrays.asList(string);
		bs.foodlist =foodlist;
	}
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		if(person.getVariable(VConstants.leader) == null){
			return null;
		}
		//if runaway variable set
		//coordinate to a new fmd away from the md variable running away from
		IPhysical pmd = (IPhysical) person.getVariable(VConstants.runaway);
		if(pmd != null){
			person.getGroup().askAll(new Move(fullMapData.getData(person),"regroup"),person,person.getGroup().getFamily());
			person.unsetVariable(VConstants.runaway);
		}
		
		Integer variable = (Integer) person.getGroup().getStatic(DeerPack.countStart);
		Integer max = (Integer) person.getGroup().getStatic(DeerPack.countMax);
		if(variable == null){
			variable = 20;
		}
		if(max == null){
			max = 70;
		}
		if(count%variable== 0&&!bs.checkSatisfied(fullMapData)||count > max){
			person.getGroup().askAll(new MoveRandomFullMapData(1,"findingnextdeerfood"),person,person.getGroup().getFamily());
		}
		
		count++;
		return null;
	}

}
