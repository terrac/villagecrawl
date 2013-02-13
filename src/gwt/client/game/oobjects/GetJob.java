package gwt.client.game.oobjects;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class GetJob extends OObject {

	

	public GetJob() {

	}
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		//check properties.  If property is set then get associated job

		//a map of associated jobs to properties somewhere (probably not here)
		return null;
	}

	@Override
	public OObject clone() {
		return new GetJob().copyProperties(this);
	}

}
