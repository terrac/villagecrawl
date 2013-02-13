package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class MakeItem extends OObjectB {
	public MakeItem() {
	}

	


	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		String owned = person.getS(VConstants.owned);
		if (owned != null) {
			Item item=ComplexCityGenerator.createItem(owned);
			person.getItemsCreate().add(item);
		}
		return null;
	}

	

	@Override
	public OObject clone() {
		return new MakeItem().copyProperties(this);
	}
}
