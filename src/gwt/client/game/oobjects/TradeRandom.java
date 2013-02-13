package gwt.client.game.oobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.display.InventoryDisplay;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UImage;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.QuestGenerator;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.output.html.GCanvas;
import gwt.shared.ClientBuild;

public class TradeRandom extends OObject {

	private static final String overlaytraded = "/images/overlay/traded.png";

	public TradeRandom() {

	}

	//treas only
	boolean getMarketOnly(){
		return true;
	}
	
	boolean treas;
	DisplayAndOk dao;

	InventoryDisplay traderInv;
	InventoryDisplay tradeeInv;
	Label moneyDisplay;
	int moneyAmount;

	Label lBuyerMoney;
	Label lPlayerMoney;

	LivingBeing tradee;
	LivingBeing trader;

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {

		// seems kinda silly
		// person.put(VConstants.money, person.getInt(VConstants.wealth));

		// find a person within 5 places
		HashMapData hmd = fullMapData.getNearKeyValue(VConstants.livingbeing
				+ "." + VConstants.team, "1", person, 10);
		// decide between buying or selling randomly
		if (hmd == null) {
			return null;
		}
		boolean sell = VConstants.getRandom().nextBoolean();
		LivingBeing otherPerson = hmd.getLivingBeing();

		if (otherPerson.getItems() == null) {
			sell = true;
		}
		// sell
		// get an item from the persons inventory randomly

		final Item item = getRandomItem(person);
		tradee = otherPerson;
		trader = person;

		if (item == null) {
			return null;
		}
		// get the value of the item and randomly add 5 0 or -5
		final int value = item.getTotalValue();
		// pop up the trade window
		HtmlOut htmlOut = EntryPoint.game.getHtmlOut();
		traderInv = new InventoryDisplay("Trader:", trader);
		tradeeInv = new InventoryDisplay("Player:", tradee);
		tradeeInv.init();
		traderInv.init();
		
		traderInv.execute(null);
		tradeeInv.execute(null);
		traderInv.getBm().selected = new Point(VConstants.getRandom().nextInt(
				traderInv.getBm().getBagMap().getXsize()), 0);
		tradeeInv.getBm().selected = new Point(VConstants.getRandom().nextInt(
				tradeeInv.getBm().getBagMap().getXsize()), 0);

		swapItems();

		if (htmlOut.currentFMD != fullMapData) {

			doTrade("normal");
			return null;
		}
		final Label counter = new Label("30");

		// counter ticks down from 10 seconds
		String text = trader.getType()
				+ " has chosen a trade.  Alter it or accept it";
		Label label = new HTML(text);

		// dao = new DisplayAndOk(new UVerticalPanel(new
		// Image(item.getImage()),label,vp,counter));

		// GCanvas gcanvas = new GCanvas(600, 300);
		Image im1 = new Image(trader.getBigImage());
		Image im2 = new Image(tradee.getBigImage());
		im1.setSize("268px", "332px");
		im2.setSize("268px", "332px");
		VerticalPanel i1vp = new VerticalPanel();
		i1vp.add(new Label("trader"));
		i1vp.add(im1);

		VerticalPanel i2vp = new VerticalPanel();
		i2vp.add(new Label("player"));
		i2vp.add(im2);

		Label l3 = new Label(text);
		moneyDisplay = new Label("trade value: 0");
		moneyDisplay.getElement().getStyle().setFontSize(3, Unit.EM);
		

		lPlayerMoney = new Label();
		lBuyerMoney = new Label();
		lPlayerMoney.getElement().getStyle().setFontSize(3, Unit.EM);
		lBuyerMoney.getElement().getStyle().setFontSize(3, Unit.EM);
		
		
		
		updateMoney();

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(i1vp);
		VerticalPanel vp2 = new VerticalPanel();
		hp.add(vp2);
		vp2.add(lBuyerMoney);
		vp2.add(lPlayerMoney);
		vp2.add(l3);
		vp2.add(moneyDisplay);


		setupSwap(vp2);

		hp.add(i2vp);

		String[] iA = new String[] { "expensive", "high priced", "normal",
				"low priced", "dirt cheap" };
		{
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			for (String a : iA) {

				Button b = new Button(a);
				b.setSize("4em", "3em");
				b.getElement().getStyle().setFontSize(3, Unit.EM);
				b.addClickHandler(new BHandler(a));
				horizontalPanel.add(b);
			}
			vp2.add(horizontalPanel);
		}
		calculateTradeAmount();
		// add to the inventories either a copy to buy or copy to sell vparam

		// create a a timer that moves

		// may need to add a "UHtmlElement" object
		dao = new DisplayAndOk(new UVerticalPanel(hp));

		dao.execute(null);
		// Timer timer = new Timer() {
		// int count = 30;
		// @Override
		// public void run() {
		// //tick down counter
		// count--;
		// counter.setText(""+count);
		// if(count == 0){
		// dao.end();
		// cancel();
		// }
		// }
		// };
		// timer.scheduleRepeating(1000);
		return null;
	}

	

	public void updateMoney() {
		lBuyerMoney.setText("traders's money:" + getMoney(traderInv));
		lPlayerMoney.setText("player's money:" + getMoney(getPlayerChoice()));
	}

	private int getMoney(InventoryDisplay traderInv2) {
		int value = 0;
		for (Item it : ((Items) traderInv2.getMapData(VConstants.items)
				.getParent().get(VConstants.items)).values()) {
			if (it.getB(VConstants.money)) {
				value += it.getTotalValue();
			}
		}
		return value;
	}

	public void setupSwap(VerticalPanel vp2) {
		HorizontalPanel hp = new HorizontalPanel();
		Button swiButton = new Button("Switch");
		swiButton.setSize("7em", "3em");
		swiButton.getElement().getStyle().setFontSize(3, Unit.EM);
		
		vp2.add(traderInv.getWidget());

		vp2.add(tradeeInv.getWidget());

		// marketInv.getWidget().setVisible(treasFirst);

		swiButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (treas) {
					tradeeInv.refreshData(tradee);
				} else {
					tradeeInv.refreshData(EntryPoint.game.getPBase(VConstants.market));

				}
				traderInv.refreshData(trader);
				traderInv.execute(null);
				tradeeInv.execute(null);

				treas = !treas;
				updateMoney();
			}
		});



		Button swap = new Button("Swap");
		swap.setSize("7em", "3em");
		swap.getElement().getStyle().setFontSize(3, Unit.EM);
		
		swap.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				swapItems();

			}
		});
		vp2.add(hp);
		if(!getMarketOnly()){
			hp.add(swiButton);				
		}
		hp.add(swap);
	}


	private Item getRandomItem(LivingBeing person) {

		if (person.getItems() == null) {
			return null;

		}
		Item[] itemA = person.getItems().values().toArray(new Item[0]);
		return VConstants.getRandomFromList(Arrays.asList(itemA));
	}

	@Override
	public OObject clone() {
		return new TradeRandom().copyProperties(this);
	}

	class BHandler implements ClickHandler {

		String value;

		@Override
		public void onClick(ClickEvent event) {
			// doTrade(item, tradee, trader, value);
			doTrade(value);
			dao.end();
		}

		public BHandler(String value) {
			super();
			this.value = value;
		}

	}

	public void doTrade(String valueType) {
		// run through items and check properties against the person's likes

		// for every distinct property add a rank

		// check balance

		if (moneyAmount < 0) {
			// get the trader actual inventory
			// get the tradee or market inventory (maybe put that in a
			// physical location)
			Items trIt = (Items) traderInv.get(VConstants.items);
			Items pcIt = (Items) getPlayerChoice().get(VConstants.items);
			finalizeTrade(trIt, pcIt);
			// go through trader and remove money items equivalent to the money
			// amount needed

			// move the switch items and the money amounts added

		} else {
			Items trIt = (Items) traderInv.get(VConstants.items);
			Items pcIt = (Items) getPlayerChoice().get(VConstants.items);
			finalizeTrade(pcIt, trIt);

		}
		// if negative check npc money (run through inventory and sum up value
		// of items with money tag)
		// traderInv.getBm().getBagMap()
		// if positive check person money

		// if needed grab the appropriate money items and create the visual
		// effect

		// for each point on the overlaymap in the current inventory and the npc
		// inventory

		// create at middle of the other side the visual effect

		//
		// LogDisplay.log(item.getName() + " traded for " + value, 2);
	}

	public void finalizeTrade(Items trIt, Items pcIt) {
		int money = Math.abs(moneyAmount);
		List<Item> iList = new ArrayList<Item>();
		for (Item it : trIt.values()) {
			if (it.getB(VConstants.money)) {
				money -= it.getTotalValue();
				iList.add(it);
			}
			if (money < 0) {
				Item iOrig = iList.get(iList.size() - 1);
				Item iClone = iOrig.clone();
				iList.set(iList.size() - 1, iClone);
				iOrig.setAmount(iOrig.getAmount() - money
						/ iOrig.getItemValue());
				iClone.setAmount(iClone.getAmount() - iOrig.getAmount());

				break;
			}
		}
		QuestGenerator qg = (QuestGenerator) EntryPoint.game.get(VConstants.quest);
		qg.addTrade(money > 0,iList);
		if(money > 0){
			LogDisplay.log("Trade failed.  Not enough money", 2);
			
			return;
		}
		pcIt.add(iList);

		trIt.getParent().put(VConstants.items,
				trIt);
		pcIt.getParent().put(VConstants.items,
				pcIt);
		LogDisplay.log("Traded "+moneyAmount+" ", 2);
	}



	public void calculateTradeAmount() {
		// take the currently visible human traded
		int value = 0;
		for (MapData md : getPlayerChoice().getBm().getBagMap()) {
			if (md == null) {
				continue;
			}
			Item it = (Item) md.get(AttachUtil.OBJECT);
			if (it != null) {
				int tv = it.getTotalValue();
				if (md.containsKey(VConstants.overlay)) {

					value += tv;
				}
			}
		}
		for (MapData md : traderInv.getBm().getBagMap()) {
			if (md == null) {
				continue;
			}
			Item it = (Item) md.get(AttachUtil.OBJECT);
			if (it != null) {
				int tv = it.getTotalValue();
				if (md.containsKey(VConstants.overlay)) {
					value -= tv;
				}
			}
		}
		if(moneyDisplay != null){
			moneyDisplay.setText("trade value:" + value);				
		}
		moneyAmount = value;
		// take the npc traded
	}

	public InventoryDisplay getPlayerChoice() {
		return tradeeInv;
	}

	public void swapItems() {
		InventoryDisplay playerCh = getPlayerChoice();
		// take the not tradeeInv and the tradeeInv or the market (whichevery
		// is
		// visible)

		// if two things are selected swap them and remove the
		// selections
		if (playerCh.getBm().selected == null
				|| traderInv.getBm().selected == null) {
			Window.alert("Select one thing from each side");
			return;
		}
		// if one or zero is do a popup

		// add some sort of "traded" overlay map (or maybe just draw
		// some text with the value across the top)
		PBase pb1 = playerCh.getBm().getBagMap()
				.getData(playerCh.getBm().selected);
		PBase pb2 = traderInv.getBm().getBagMap()
				.getData(traderInv.getBm().selected);
		if (pb1 != null && pb1.get(AttachUtil.OBJECT) != null) {
			if (pb1.containsKey(VConstants.overlay)) {
				pb1.remove(VConstants.overlay);
			} else {
				pb1.put(VConstants.overlay, overlaytraded);
			}
		}

		if (pb2 != null && pb2.get(AttachUtil.OBJECT) != null) {
			if (pb2.containsKey(VConstants.overlay)) {
				pb2.remove(VConstants.overlay);
			} else {
				pb2.put(VConstants.overlay, overlaytraded);
			}
		}

		traderInv.getBm().getBagMap()
				.setData(traderInv.getBm().selected, (MapData) pb1);
		playerCh.getBm().getBagMap()
				.setData(playerCh.getBm().selected, (MapData) pb2);

		calculateTradeAmount();
		traderInv.update();
		playerCh.update();
	}
}
