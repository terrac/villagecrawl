package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.game.GameUtil;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.item.Item;
import gwt.client.main.Economy;
import gwt.client.main.MoveClosest;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class AddToMarket extends OObject{
	
	
	public AddToMarket() {
	}

	
	@Override
	public OObject clone() {
		
		return new AddToMarket().copyProperties(this);
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		String resource=person.getS(VConstants.resource);
		int gdp = EntryPoint.game.getInt(VConstants.GDP);
		gdp++;
		EntryPoint.game.getHtmlOut().setGDP(gdp);
		
		
//		DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(new UImage(item.getImage())));
//		displayPopup.displaypopup(person, null,5);		
		return new Returnable(0);
		//note, if you want random then there needs to be an interface  that gets called if it exists
//		final Item item = (Item) person.getParent().getPBase(VConstants.gate).getPBase(VConstants.item).clone();
//		addToList(person, new MoveClosest(VConstants.gate,
//							"market", "main"));
//		
//		addToList(person, new OObject() {
//			
//			@Override
//			public Returnable execute(FullMapData fullMapData, LivingBeing person) {
//				Items it=(Items) EntryPoint.game.getPBase(VConstants.market).get(VConstants.items);
//				
//				it.add(item);
//				person.getItemsCreate().add(GameUtil.getItemFavor(item));
//				DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(new UImage(item.getImage())));
//				displayPopup.displaypopup(person, null,5);
//				return null;
//			}
//		});
//		return null;
	}

}
