package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.oobjects.CheckStat;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Economy extends VParams {


	public Economy() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Economy(Object... vp) {
		super(vp);
		// TODO Auto-generated constructor stub
	}



	/**
	 * could handle creating objects for individuals or other stuff, would get called during people's turns
	 */
	@Override
	public void execute(Map<String, Object> map) {
		for(Object vp : getList(VConstants.vparams)){
			if(((Item) map.get(AttachUtil.OBJECT)).getCalculatedValue() != 0){
				break;
			}
			((VParams) AttachUtil.getMappedParam(vp)).execute(map);
			
		}
	}
	
	

	@Override
	public Economy clone() {

		return new Economy().copyDeepProperties(this);
	}


}
