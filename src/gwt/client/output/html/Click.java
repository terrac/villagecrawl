/**
 * 
 */
package gwt.client.output.html;

import gwt.client.output.MainPanel;
import gwt.client.output.SEventClick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Click implements MouseDownHandler {

	SEventClick<GCanvas> mouseDown;
	@Override
	public void onMouseDown(MouseDownEvent event) {

		mouseDown.execute(event.getX(), event.getY(), (GCanvas) event
				.getSource());
	}

	public Click(SEventClick<GCanvas> mouseDown) {

		this.mouseDown = mouseDown;
	}

}