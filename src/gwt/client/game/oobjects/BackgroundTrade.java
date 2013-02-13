package gwt.client.game.oobjects;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

public class BackgroundTrade extends OObject {

	public BackgroundTrade() {

	}

	public BackgroundTrade(boolean buy) {
		put(VConstants.buy, buy);
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		boolean buy = getB(VConstants.buy);
		for (LivingBeing lb : fullMapData.people) {
			if ("itemshop".equals(lb.getS(VConstants.owned))) {
				LivingBeing buyer = lb;
				LivingBeing seller = person;

				if (buy) {
					buyer = person;
					seller = lb;

				}
				if (!buy) {
					//buy(person, lb, buyer, Haggle.generateItem(seller));
					//LogDisplay.log(buyer.getItems(), 2);
					return null;
				}
				for (Item it : seller.getItemsCreate().values()) {
					if (!it.getB(VConstants.money)&&it.getAmount() > 5 + VConstants.getRandom().nextInt(4)) {
						buy(person, lb, buyer, 10, 1);
						//LogDisplay.log(seller.getItems(), 2);
						return null;
					}
				}
			}
		}

		return null;
	}

	//traded goes to % bias
	public static void buy(LivingBeing person, LivingBeing seller,
			LivingBeing buyer, int moneyamount, double bias) {
		HashMapData hmd = seller.getParent();
		
		int ma = buyer.getItemsCreate().getMoneyAmount();
		if(ma < moneyamount){
			moneyamount=ma;			
		}
		if(moneyamount > seller.getItemsCreate().getTotalValue()){
			moneyamount = seller.getItems().getTotalValue();
		}
		Items itemsBought=buyer.getItems().subtractMoneyAndThenItems(moneyamount);
		moneyamount = (int) (itemsBought.getTotalValue() * bias);
		
		
		Items itemsSold = seller.getItemsCreate().subtractItems(moneyamount);
		String toDisp = itemsSold.getImage();
		if(toDisp == null){
			toDisp = itemsBought.getImage();
		}
		LogDisplay.log(itemsSold.getTotalValue()+" "+itemsBought.getTotalValue(), 2);
		buyer.getItems().add(itemsSold);
		seller.getItems().add(itemsBought);
		OObject.addToList(person, new Move(hmd, "need"));
		
		
		DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
				new UImage(person.getImage()), new UImage(toDisp)));
		displayPopup.displaypopup(hmd.getLivingBeing(), null, 5);

		
	
		if (VConstants.getRandom().nextDouble() < .05) {
			RandomEffects.nextRandom();
		}
	}

	@Override
	public OObject clone() {
		return new BackgroundTrade().copyProperties(this);
	}

}
