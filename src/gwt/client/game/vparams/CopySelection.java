package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class CopySelection extends VParams {

	public CopySelection(String string) {
		put(VConstants.selectionname,string);
	}
	public CopySelection() {
	
	}
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmd=(HashMapData) map.get(AttachUtil.OBJECT);
		
		if(hmd.getLivingBeing() != null){
			return;
		}
		if(hmd.containsKey(VConstants.target)){
			EntryPoint.game.getHtmlOut().currentFMD = hmd.getParent().getParent().getData((Point) hmd.get(VConstants.portal));
			return;
		}
		
		String selectionpb = getS(VConstants.selectionname);
		MapData o=(MapData) VExpression.getValue(selectionpb, EntryPoint.game);
		
		if(o == null){
			return;
		}
		if(o.containsKey(VConstants.requirement)){
			if(!o.get(VConstants.requirement).equals(hmd.getPBase(o.getKey()).get(VConstants.requirement))){
				return;
			}
		}
		
		VExpression.setValue(selectionpb, null, EntryPoint.game);
		
		hmd.remove(o.getKey());
		hmd.putAppropriate( o);
		hmd.getParent().addType(o.getValue());
	}
	@Override
	public PBase clone() {
		
		return new CopySelection().copyProperties(this);
	}
}
