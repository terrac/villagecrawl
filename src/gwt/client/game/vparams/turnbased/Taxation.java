package gwt.client.game.vparams.turnbased;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

public class Taxation extends VParams{

	
	public Taxation() {	
	}
	int count = 0;
	@Override
	public void execute(Map<String, Object> map) {
		count++;
		if(count < 500){
			
			return;
		}
		count = 0;
//		2 run through all the people
		List<LivingBeing> people = EntryPoint.game.getHtmlOut().currentFMD.people;
		for(LivingBeing lb : people.toArray(new LivingBeing[0])){
			if(!lb.containsKey(VConstants.owned)){
				continue;
			}
			lb.getItemsCreate().subtractMoneyAndThenItems(10);
			DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
					new UImage(lb.getImage()), new UImage("/images/gold.png")));
			displayPopup.displaypopup(lb, null, 1);

			//display a coin and do a cha chin
			if(lb.getItems().getTotalValue() < 1){
				//change clothing to rags
				lb.getItems().put(VConstants.body, EntryPoint.game.getItem("rags"));
				lb.death();
				if("itemshop".equals(lb.getS(VConstants.owned))){
					Window.alert("You lose");
					//maybe throw an error here
				}
			}
		}
//	     3 subtract X amount of money
//	     4 if money is 0 change the clothing to rags/remove
//	     5 if this is the itemshop person pop up a you lose alert (and throw a lose execption)
	     
	}

	@Override
	public Taxation clone() {
		
		return new Taxation().copyProperties(this);
	}
}
