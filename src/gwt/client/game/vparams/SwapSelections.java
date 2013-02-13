package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.shared.datamodel.VParams;

public class SwapSelections extends VParams {

	public SwapSelections(String string) {
		put(VConstants.selectionname,string);
	}
	public SwapSelections() {
	
	}
	@Override
	public void execute(Map<String, Object> map) {
		Object md=(Object) map.get(AttachUtil.OBJECT);
		
		if(!(md instanceof Point)){
			return;
		}
		Point toMove = (Point) md;
		PBase sPbase = getPBase(VConstants.selectionname);
		String selection = sPbase.getS(VConstants.selection);
		MapData o=(MapData) VExpression.getValue(selection, EntryPoint.game);
		
		if(o == null){
			return;
		}
		
		VExpression.setValue(selection, null, EntryPoint.game);
		BagMap bm=(BagMap) map.get(AttachUtil.attacher);
		bm.getBagMap().setData(toMove, o);
		BagMap bm2 = (BagMap) sPbase.get(AttachUtil.attacher);
		bm2.selected = null;
		}
	@Override
	public PBase clone() {
		
		return new SwapSelections().copyProperties(this);
	}
}
