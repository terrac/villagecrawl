package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.vparams.quest.MarketSubQuest;
import gwt.client.game.vparams.quest.SubQuest;
import gwt.client.game.vparams.quest.TradeResult;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class QuestGenerator extends VParams {

	public QuestGenerator() {

	}


	@Override
	public void execute(Map<String, Object> map) {
		
		List<VParams> activeList = (List<VParams>)getListCreate(VConstants.activelist);
		for(VParams vp :activeList){
			VParams checkEnd = (VParams) vp.get(VConstants.end);
			if(checkEnd == null){
				activeList.remove(vp);
			}
			checkEnd.execute(map);
			if(map.containsKey(VConstants.success)){
				activeList.remove(vp);
				
				LogDisplay.log(checkEnd.getS(VConstants.message), 2);
				VParams next = (VParams) checkEnd.get(VConstants.nextquest);
				if(next != null){
					activeList.add(next);
				}
				break;
			}
		}
		if(activeList.size() > 0){
			return;
		}
		
		for(SubQuest sq :getSubQuests()){
			VParams vp =sq.getQuest();
			if(vp != null){
				vp.execute(map);
				activeList.add(vp);
				break;	
			}
			
		}
	}


	public List<SubQuest> getSubQuests() {
		return (List<SubQuest>) getList(VConstants.list);
	}

	@Override
	public PBase clone() {

		return new QuestGenerator().copyProperties(this);
	}


	public void addTrade(boolean b, List<Item> iList) {
		for(SubQuest sq : getSubQuests()){
			if(sq instanceof TradeResult){
				((TradeResult)sq).addTrade(b,iList);
			}
			
		}
	}


	public void addSubQuest(SubQuest marketQuest) {
		getListCreate(VConstants.list).add(marketQuest);
	}

}
