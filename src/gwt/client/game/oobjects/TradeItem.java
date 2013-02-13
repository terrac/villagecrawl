package gwt.client.game.oobjects;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
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
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

public class TradeItem extends VParams {

	public TradeItem() {

	}

	public TradeItem(String need) {
		put(VConstants.need, need);
	}

	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing person = getLivingBeing(map);
		FullMapData fullMapData = person.getParent().getParent();
		// get need for type of item to be traded
		String need = getS(VConstants.need);

		HashMapData hmd = fullMapData.getNearKeyValue("livingbeing.owned",
				need, person, 40);
		if (hmd == null) {
			return;
		}
		Item it = hmd.getLivingBeing().getItems().getFirstByProperty(need);
		if (it != null) {
			Item newit = it.subtract(1);
			person.getItems().add(newit);
			OObject.addToList(person, new Move(hmd, "need"));
			DisplayPopup displayPopup = new DisplayPopup(
					ClientBuild.list(new UImage(person.getImage()), new UImage(
							newit.getImage())));
			displayPopup.displaypopup(hmd.getLivingBeing(), null, 5);

		}
	}

	@Override
	public VParams clone() {
		return new TradeItem().copyProperties(this);
	}

}
