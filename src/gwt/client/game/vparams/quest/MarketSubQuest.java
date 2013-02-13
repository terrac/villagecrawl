package gwt.client.game.vparams.quest;

import java.util.Arrays;
import java.util.List;

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

public class MarketSubQuest extends PBase implements SubQuest,TradeResult{
	
	public MarketSubQuest() {
	}

	@Override
	public void addTrade(boolean b, List<Item> iList) {
		// TODO Auto-generated method stub
		getListCreate(VConstants.list).addAll(Arrays.asList(iList));
	}

	@Override
	public VParams getQuest() {
		Item[] listCreate = (Item[]) getListCreate(VConstants.list).toArray(new Item[0]);
		for(Item it : listCreate){
			getList(VConstants.list).remove(it);
			if(VConstants.getRandom().nextInt(100) < 20){
				
				return new MarketQuest(it);
			}
		}
		return null;
	}


	@Override
	public PBase clone() {

		return new MarketSubQuest().copyProperties(this);
	}

}
