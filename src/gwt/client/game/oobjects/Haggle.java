package gwt.client.game.oobjects;

import gwt.client.EntryPoint;
import gwt.client.game.SellOne;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UImage;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.item.Item;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.client.output.OutputDirector;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class Haggle extends OObject {

	private static final String overlaytraded = "/images/overlay/traded.png";

	public Haggle() {

	}

	DisplayAndOk dao;
	Integer initialValue = null;
	int offered;
	Item item;
	String text;
	double curDouble;

	boolean using;
	static int count = 0;

	LivingBeing tradeOther;
	Button acceptButton;

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		tradeOther = SellOne.getPerson("itemshop", fullMapData);
		getItem(person);

		computeHaggle();

		showHaggle(person);

		return null;
	}

	protected void getItem(LivingBeing person) {
		Item it = generateItem(person);
		item = it;
	}

	public static Item generateItem(LivingBeing person) {
		PBase culture = EntryPoint.game.getPBase(VConstants.culture).getPBase(
				person.getType().substring(0, person.getType().indexOf(' ')));
		if (culture == null) {
			Window.alert(person.getType() + " has no items");
		}
		// item=(Item)VConstants.getRandomFromList(EntryPoint.game.getPBase(VConstants.culture).getList(VConstants.items));
		String typeName = (String) VConstants.getRandomFromPBase2(culture
				.getPBase(VConstants.random));
		PBase assocType = culture.getType(VConstants.items).getPBase(typeName);
		if (assocType == null) {
			assocType = EntryPoint.game.getPBase(VConstants.culture)
					.getPBase(VConstants.items).getPBase(typeName);
		}
		Item it = (Item) VConstants.getRandomFromList(assocType
				.getList(VConstants.list));
		it.setAmount(10);
		return it;
	}

	public boolean computeHaggle() {

		List<VParams> tagCheckList = (List<VParams>) getListCreate(VConstants.list);
		Map map = new HashMap();
		map.put(VConstants.taggenerator, new HashMap());
		for (VParams vp : tagCheckList) {
			// the tag checkers add tags to a value on the map
			vp.execute(map);

		}
		// turn counts into percentages
		Map<String, Integer> si = (Map<String, Integer>) map
				.get(VConstants.taggenerator);
		for (Entry<String, Integer> e : si.entrySet()) {
			e.setValue(e.getValue() * 10);
		}
		// go through all of the tags and the percentages and
		// decide on one through randomness
		String generic = "generic";
		int percent = 10;
		for (Entry<String, Integer> e : si.entrySet()) {
			if (VConstants.getRandom().nextInt(100) < e.getValue()) {
				generic = e.getKey();
				percent = e.getValue();
				break;
			}
		}
		// then take the percentage and decide on a price
		// put the price in the map and let haggle use
		// int value=(int) (item.getTotalValue() + (percent*.07 *
		// item.getTotalValue()));
		List<String> types = item.getTypes();
		if (types != null) {
			for (String t : types) {
				List<VParams> vpList = EntryPoint.game
						.getPBase(VConstants.culture).getType(VConstants.items)
						.getPBase(t).getList(VConstants.formula);
				if (vpList != null) {
					for (VParams vp : vpList) {
						vp.execute(map);
					}
				}

			}
		}

		Double formulascale = (Double) map.get(VConstants.formula);
		int value = item.getTotalValue();
		// if (formulascale != null) {
		// value += (int) (item.getTotalValue() * formulascale);
		// }

		if (initialValue == null) {
			initialValue = item.getTotalValue();
			// get the haggle value and determine offered by that
			curDouble = VConstants.getRandom().nextDouble();

			value = (int) (curDouble * value + value - curDouble * value * .3);
			text = value + getInitialOffer();
			offered = value;
		} else {
			double d = VConstants.getRandom().nextDouble() * .15 + .1;
			curDouble = curDouble - d;
			offered = (int) (offered - offered * d);

			text = offered + " is the price. \n" + getLine(generic) + "\n";// +
																			// initialValue;

		}

		if (offered < (initialValue * .7)) {
			int gdp = EntryPoint.game.getInt(VConstants.GDP);
			gdp -= 20;
			EntryPoint.game.getHtmlOut().setGDP(gdp);
			LogDisplay.log("trade declined", 2);
			return false;
		}
		if (acceptButton != null) {

			acceptButton
					.setEnabled(tradeOther.getItems().getTotalValue() < offered);

		}
		return true;
	}

	protected String getInitialOffer() {
		return " is the initial value offered";
	}

	public String getLine(String specific) {
		PBase pBase = EntryPoint.game.getPBase(VConstants.taggenerator);
		PBase cPB = null;
		for (Object o : item.getList(VConstants.type)) {
			if (pBase.containsKey((String) o)) {
				cPB = VConstants.getRandomFromList(pBase.getPBase((String) o)
						.getList(VConstants.list), curDouble);
			}
		}
		if (cPB == null) {

			cPB = VConstants.getRandomFromList(pBase.getList(VConstants.list),
					curDouble);
		}

		if (cPB == null) {
			return "I don't know what to say";
		}
		return (String) VConstants.getRandomFromList(cPB
				.getList(VConstants.list));
	}

	public void showHaggle(final LivingBeing person) {
		if (initialValue > tradeOther.getItems().getTotalValue()) {
			LogDisplay.log("trade declined(initial value of " + initialValue
					+ " is too much for " + tradeOther.getName()
					+ " which has " + tradeOther.getItems().getTotalValue(), 2);
			OutputDirector.soundPlayer.playOnce("bong");

			return;
		}

		Label label = new Label(text);
		HorizontalPanel hp = new HorizontalPanel();
		addImage(hp, person);

		acceptButton = new Button("Accept", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int gdp = EntryPoint.game.getInt(VConstants.GDP);
				gdp += (1 - curDouble) * 100;
				if (gdp > 50) {
					DisplayPopup displayPopup = new DisplayPopup(
							ClientBuild.list(new UImage("/images/gold.png")));
					displayPopup.displaypopup(person, null, 5);

					count++;
				}
				EntryPoint.game.getHtmlOut().setGDP(gdp);

				if (count > 3) {

					OutputDirector.soundPlayer.playOnce("ding2");
				} else {
					OutputDirector.soundPlayer.playOnce("ding");

				}
				Items it = tradeOther.getItems().subtractMoneyAndThenItems(
						offered);
				person.getItemsCreate().add(it);
				tradeOther.getItemsCreate().add(item);
				LogDisplay.log(initialValue + " " + item.getTotalValue() + " "
						+ offered + " "
						+ tradeOther.getItemsCreate().getTotalValue(), 2);

				runAccept();
				dao.end();
			}

		});

		hp.add(acceptButton);
		LogDisplay.log(tradeOther.getItems().getTotalValue(), 2);

		if (tradeOther.getItems().getTotalValue() < offered) {
			acceptButton.setEnabled(false);
		}

		Button decline = new Button("Decline", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dao.end();

				if (!computeHaggle()) {
					OutputDirector.soundPlayer.playOnce("bong");
					return;
				}
				OutputDirector.soundPlayer.playOnce("dinglow");
				showHaggle(person);
			}
		});
		hp.add(decline);

		label.getElement().getStyle().setFontSize(4, Unit.EM);
		acceptButton.getElement().getStyle().setFontSize(4, Unit.EM);
		decline.getElement().getStyle().setFontSize(4, Unit.EM);
		Image image = new Image(item.getImage());
		image.setSize("64px", "64px");
		hp.add(image);
		hp.add(new Label(item.getName() + "Amount: " + item.getAmount()
				+ " Value: " + item.getItemValue()));
		dao = new DisplayAndOk(new UVerticalPanel(label, hp));

		dao.execute(null);
	}

	protected void addImage(HorizontalPanel hp, LivingBeing person) {

	}

	protected void runAccept() {

	}

	@Override
	public OObject clone() {
		return new Haggle().copyProperties(this);
	}
}