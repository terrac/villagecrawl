package gwt.client.game.vparams;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class SetCurrentFMD extends VParams {

	public SetCurrentFMD() {
	}
	
	public SetCurrentFMD(int x, int y) {
		put(VConstants.xsymbolic,x);
		put(VConstants.ysymbolic,y);
	}
	@Override
	public void execute(Map<String, Object> map) {
		EntryPoint.game.getHtmlOut().currentFMD = EntryPoint.game.getMapArea().getMap().getData(getInt(VConstants.xsymbolic), getInt(VConstants.ysymbolic));
		
	}
	
	@Override
	public PBase clone() {
		return new SetCurrentFMD().copyProperties(this);
	}
}
