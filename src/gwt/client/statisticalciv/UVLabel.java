package gwt.client.statisticalciv;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.game.display.UIVParams;

public class UVLabel extends UIVParams{
	Label l;
	String message;
	public UVLabel() {
	}
	
	
	public UVLabel(String message) {
		this.message = message;
	}


	@Override
	public Widget getWidget() {
		
		return l;
	}
	@Override
	public void init() {
		l = new Label(message);
	}
	public void setText(String text){
		
		l.setText(text);
	}

	@Override
	public UIVParams clone() {
		return new UVLabel().copyProperties(this);
	}
}
