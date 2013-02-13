package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.shared.datamodel.VParams;

import java.util.List;
import java.util.Map;

public class CloneDeposit extends VParams {
	
	public CloneDeposit() {
		
	}
	public CloneDeposit(String key) {
		put(VConstants.checknegative,key);
	}
	@Override
	public void execute(Map<String, Object> map) {
		// gets the clicked on hmd and clones a copy, then puts that at the
		// deposit point

		// the clone with the clone adding probably is no longer necessary

		MapData md = (MapData) map.get(AttachUtil.OBJECT);
		if(md == null){
			return;
		}
		
		String checkNegative = getS(VConstants.checknegative);
		
		if(checkNegative != null){
			Integer i=(Integer) VExpression.getValue(map, checkNegative);
			if(i < 0){
				VExpression.setValue(map, checkNegative, i + md.getInt(VConstants.gold));
				
				return;
			}
			
		}
		
		if(map.get(AttachUtil.OBJECT) instanceof Point){
			return;
		}
		
		PBase attacher = (PBase) map.get(AttachUtil.attacher);

		List p= (List) attacher.get(BagMap.depositpoint);
		HashMapData hmd=EntryPoint.game.getHtmlOut().currentFMD.getData(new Point(p));
		//HashMapData hmd = fmd.getData(new Point((List) p.get(0)));
		// person (or whatever) plus clone

		
		HashMapData nearestEmpty = hmd.getParent().getNearestEmpty(hmd);
		if (md instanceof LivingBeing) {
			nearestEmpty.putLivingBeing((LivingBeing) md.clone());
			
		} else {
			nearestEmpty.put(md.clone());
		}

			

		EntryPoint.game.getHtmlOut().refreshFmds();
	}
	@Override
	public PBase clone() {
		
		return new CloneDeposit().copyProperties(this);
	}
}
