package gwt.client.game.vparams;

import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class CategoryMap extends VParams {

	public CategoryMap() {
		
	}
	
	public CategoryMap(String type,String message) {
		put(VConstants.message,message);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		Window.alert(getS(VConstants.message));
	}
	@Override
	public PBase clone() {
		
		return new CategoryMap().copyProperties(this);
	}

	public void addLine(String string, String string2) {
		
		getListCreate(VConstants.list).add(new String[]{string,string2});
		
		
	}
	
}
