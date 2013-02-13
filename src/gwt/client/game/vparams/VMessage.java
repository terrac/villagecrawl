package gwt.client.game.vparams;

import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class VMessage extends VParams {

	public VMessage() {
		
	}
	
	public VMessage(String message) {
		put(VConstants.message,message);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		Window.alert(getS(VConstants.message));
	}
	@Override
	public PBase clone() {
		
		return new VMessage().copyProperties(this);
	}
	
}
