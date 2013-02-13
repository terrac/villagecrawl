package gwt.client.game.vparams.quest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.shared.datamodel.VParams;

public class MarketQuest extends VParams{
	
	public MarketQuest() {
	}
	
	public MarketQuest(Item item) {
		put(VConstants.item,item);
		
	}

	@Override
	public void execute(Map<String, Object> map) {
		Item item = (Item) get(VConstants.item);
		
		//pop up the displaychoices with the quest starting stuff
		
		//if the player chooses yes
		//set the appropriate spot in the bagmap
		
	}
	@Override
	public PBase clone() {

		return new MarketQuest().copyProperties(this);
	}

}
