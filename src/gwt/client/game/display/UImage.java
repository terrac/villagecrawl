package gwt.client.game.display;

import java.util.List;
import java.util.Map;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class UImage extends UIVParams {

	public UImage() {
	}

	public UImage(String image) {
		put(VConstants.image, image);
	}

	Image image;

	
	@Override
	public void execute(Map<String, Object> map) {
	}
	@Override
	public void init() {
		String imname = getS(VConstants.image);
		if(imname == null){
			return;
		}
		image = new Image(imname);
	}

	@Override
	public Widget getWidget() {
		return image;
	}
}
