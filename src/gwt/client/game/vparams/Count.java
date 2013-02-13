package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class Count extends OObject {
	public Count() {
		
	}
	
	public Count(int count, VParams vp) {
		put(VConstants.count, count);
		put(VConstants.vparams,vp);
	}
	
	

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		Integer count =(Integer) person.get(VConstants.count);
		if(count == null){
			count = getInt(VConstants.count);
		}
		if(count < 0){
			VParams vparams=(VParams) get(VConstants.vparams);
			
			vparams.execute(AttachUtil.createMap(person, this));
		}
		person.put(VConstants.count,count-1);
			return null;
	}
	@Override
	public OObject clone() {

		return new Count().copyProperties(this);
	}
}
