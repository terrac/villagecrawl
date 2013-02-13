package gwt.client.game.display;

import java.util.List;
import java.util.Map;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UVerticalPanel extends UIVParams {

	public UVerticalPanel() {
	}

	public UVerticalPanel(Widget ... widgets) {
		for(Widget w : widgets){
			vp.add(w);
		}
	}

	public VerticalPanel vp = new VerticalPanel();
	
	@Override
	public void execute(Map<String, Object> map) {
	}
	@Override
	public void init() {
		//should fix this later
	}

	@Override
	public Widget getWidget() {
		return vp;
	}
}
