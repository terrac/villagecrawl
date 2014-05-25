package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.main.Game;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PauseDisplay extends UIVParams{
	
	Button but;
public PauseDisplay() {

			

		

	}

		




	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return but;
	}
	
	@Override
	public void init() {
		 but= new Button();
			but.setText("pause");
			
			but.setSize("20em", "5em");
			

			ClickHandler pause = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Game game = EntryPoint.game;
					game.pauseToggle();

				}
			};

			but.addClickHandler(pause);

	}
}
