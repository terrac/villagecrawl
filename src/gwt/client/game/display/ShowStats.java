package gwt.client.game.display;

import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.game.AttachUtil;
import gwt.client.main.base.LivingBeing;
import gwt.shared.datamodel.VParams;

public class ShowStats extends UIVParams {
	HTML label;
	
	
	@Override
	public void execute(Map<String, Object> map) {

	}
	@Override
	public void update() {
		LivingBeing lb=(LivingBeing) get(AttachUtil.OBJECT);
		label.setHTML(lb.getStats().toString().replace("\n", "<br>"));
	}
	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return label;
	}
	@Override
	public void init() {
		label  = new HTML();
	}

}
