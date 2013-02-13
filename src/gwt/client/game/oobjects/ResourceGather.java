package gwt.client.game.oobjects;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.Seed;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.DropAll;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.PickUp;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.getters.KeyValue;
import gwt.shared.datamodel.VParams;

public class ResourceGather extends OObject {

	public ResourceGather() {

	}

	
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
	
		PBase pb=EntryPoint.game.getPBase(VConstants.culture).getType(VConstants.resource).getPBase(person.getS(VConstants.owned));
		
		addToList(person, new MoveClosest(
				VConstants.gate, pb.getS(VConstants.value)));
		addToList(person, new Wait(2));

		Item item = EntryPoint.game.getItem(pb.getS(VConstants.type));
		person.getItemsCreate().add(item);
		return null;
	}

	@Override
	public OObject clone() {
		return new ResourceGather().copyProperties(this);
	}

}
