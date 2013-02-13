package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class SendMoney extends VParams{
	public SendMoney() {
		
	}
	public SendMoney(boolean b) {
	}
	public void execute(Map<String,Object> map) {
		LivingBeing lb=getLivingBeing(map);
		
		//get the associated person with the left person
		
		
		//if true then add money
		
		// if false then subtract money
		
		//if money isn't set then decide it based on the score
	}
	
	@Override
	public PBase clone() {
		
		return new SendMoney().copyProperties(this);
	}

}
