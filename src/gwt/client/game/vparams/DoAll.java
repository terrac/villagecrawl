package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class DoAll extends VParams {
	public DoAll() {
		
	}
	
	public DoAll(String type,OObject oo,int repeat) {
		put(VConstants.type, type);
		put(VConstants.oObject,oo);
		put(VConstants.repeat,repeat);
	}
	
	
	Integer count = null;
	@Override
	public void execute(Map<String, Object> map) {

		if(count != null&&count < getInt(VConstants.repeat)){
			count++;
			return;
		}
		count = 0;
		FullMapData fmd= (FullMapData) map.get(AttachUtil.OBJECT);
		
		for(LivingBeing lba :fmd.getLivingBeings()){
			if(lba.getType() != null&&lba.getType().equals(getS(VConstants.type))){
				lba.getTemplate().clear();
				lba.getTemplate().push((OObject) get(VConstants.oObject));
			}
		}
	}

	@Override
	public PBase clone() {

		return new DoAll().copyProperties(this);
	}
}
