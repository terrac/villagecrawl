package gwt.client.game;

import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.output.OutputDirector;

public class LevelUp extends PBase{

	public static final String NEXTLEVELUP = "nextlevelup";

	public void execute(LivingBeing lb) {
		int int1 = lb.getInt(NEXTLEVELUP);
		if(int1 > 600){
			return;
		}
		if(int1 <lb.getInt(VConstants.experience)){
			if(int1 == 0){
				lb.put(NEXTLEVELUP, 200);
				return;
			}
			lb.put(NEXTLEVELUP,int1 + 200);
			OutputDirector.soundPlayer.playOnce(VConstants.levelup);
			lb.getStats().put(VConstants.strength,lb.getStats().getInt(VConstants.armor)+1);
			lb.getStats().put(VConstants.maxhealth,lb.getStats().getInt(VConstants.maxhealth)+20);
		}
	}

	@Override
	public PBase clone() {
		
		return new LevelUp().copyProperties(this);
	}
}
