package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;
import gwt.client.map.HashMapData;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SpeedDisplay extends UIVParams {

	VerticalPanel panel;

	public SpeedDisplay() {

	}

	public void execute(HashMapData mapData) {

	}

	@Override
	public Widget getWidget() {

		return panel;
	}
	public TextBox tb;
	@Override
	public void init() {
		panel = new VerticalPanel();

		Label label = new Label("speed");
		panel.add(label);
		tb = new TextBox();
		tb.setSize("3em", "1em");
		panel.add(tb);
		if (EntryPoint.game != null) {
			tb.setText("" + EntryPoint.game.getSpeed());

		} else {
			tb.setText("" + 60);
		}

		tb.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				TextBox tb = (TextBox) event.getSource();
				try {
					EntryPoint.game.setSpeed(Integer.parseInt(tb.getText()));
					Cookies.setCookie(VConstants.speed, tb.getText());
				} catch (NumberFormatException e) {
					tb.setText("" + EntryPoint.game.getSpeed());
				}

			}
		});

	}
}
