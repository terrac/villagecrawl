package gwt.client.game;

import java.util.List;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.oobjects.BackgroundTrade;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;

public class SellOne extends OObject {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if (person.getItems() == null || person.getItems().size() == 0) {
			return null;
		}
		Item it = null;
		List<String> keys = null;
		for (Item item : person.getItems().values()) {
			
			keys = EntryPoint.getCulture(VConstants.sell).getList(item.getName());
			
			if(item.getB(VConstants.money)){
				continue;
			}
			it = item;
			
			if (keys != null) {
				break;
			}
		}
		
		if (keys != null&&it != null) {
			for(String key : keys){
				LivingBeing lb = getPerson(key, fullMapData);
				if (lb != null) {
					it.getParent().remove(it);
					int amount = it.getAmount();
					it = EntryPoint.game.getItem(EntryPoint.getCulture(VConstants.itemmap).getS(lb.getS(VConstants.owned)));
					it.setAmount(amount);
					lb.getItemsCreate().add(it);
					sellTo(person, lb);
					LogDisplay.log("complex trade!" + it.getName(), 2);
					return null;
				}
			}
			
		}
		if(it == null){
			return null;
		}
		if(it.getTotalValue() < 5){
			return null;
		}
		final LivingBeing lb = getPerson("itemshop", fullMapData);
		sellTo(person, lb);

		return null;
	}

	protected void sellTo(LivingBeing person,  final LivingBeing lb) {
		if(lb == null){
			return;
		}
		addToList(person, new Move(lb.getParent(), "movesell"));
		addToList(person, new OObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				BackgroundTrade.buy(person, person, lb, 10, 1);
				// LogDisplay.log(person.getItems(), 2);
				return null;
			}
		});
	}

	public static LivingBeing getPerson(String key, FullMapData fullMapData) {
		for (final LivingBeing lb : fullMapData.people) {
			if (key.equals(lb.get(VConstants.owned))) {
				return lb;
			}
		}
		return null;
	}

	@Override
	public OObject clone() {
		return new SellOne().copyProperties(this);
	}
}
