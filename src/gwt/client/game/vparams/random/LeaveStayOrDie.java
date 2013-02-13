package gwt.client.game.vparams.random;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.shared.datamodel.VParams;

public class LeaveStayOrDie extends VParams{
	
	public LeaveStayOrDie() {
	}
	
	
	
	
	public LeaveStayOrDie(int leaveChance, int stayChance, int dieChance) {
		put(VConstants.leave,leaveChance);
		put(VConstants.stay,stayChance);
		put(VConstants.die,dieChance);
	}




	public void execute(java.util.Map<String,Object> map) {
		LivingBeing lb = getLivingBeing(map);
		//
		int rand = VConstants.getRandom().nextInt(100);
		
		rand = rand - getInt(VConstants.leave);
		if(rand < 0){
			PBase questGenerator=EntryPoint.game.getPBase(VConstants.quest);
			questGenerator.getListCreate(VConstants.leave).add(lb);
			lb.getParent().remove(lb);
		}
		
		rand = rand - getInt(VConstants.stay);		
		if(rand < 0){
			if(!(lb.getParent() instanceof HashMapData)){
				PBase questGenerator=EntryPoint.game.getPBase(VConstants.quest);
				questGenerator.getListCreate(VConstants.temporary).remove(lb);
				
				EntryPoint.game.getHtmlOut().currentFMD.add(Direction.East, lb);
				
			}
		}
		rand = rand - getInt(VConstants.die);
		if(rand < 0){
			lb.death();
		}
		//
		
	}
	
	@Override
	public PBase clone() {
		
		return new LeaveStayOrDie();
	}

}
