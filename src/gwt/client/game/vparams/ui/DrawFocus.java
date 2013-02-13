package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.UIVParams;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.java.swing.plaf.windows.resources.windows;

public class DrawFocus extends VParams {

	public DrawFocus() {

	}


	public DrawFocus(String key, String value, int displaySize, PBase...results) {
		put(VConstants.key,key);
		put(VConstants.value,value);
		put(VConstants.display,displaySize);
		put(VConstants.list,Arrays.asList(results));
	}
	
	public DrawFocus(String key, String value, PBase...results) {
		this(key, value, 2, results);
	}
	
	public DrawFocus(String image,int x, int y) {
		put(VConstants.xfull,x);
		put(VConstants.yfull,y);
		put(VConstants.image,image);
	}

	@Override
	public void execute(final Map<String, Object> map) {
		//maybe eventually search through all the widget names and get the absolute position
		//through absolute left and absolute top
		//or just do a map of important ones
		//for now just this
		
		visualEffect(getS(VConstants.image),Window.getClientWidth()/2,Window.getClientHeight()/2,getInt(VConstants.xfull),getInt(VConstants.yfull));
	}
	

	public void visualEffect(String imname, int startx, int starty, int endx,
			int endy) {
		final Image imEle = new Image(imname);
		imEle.getElement().getStyle().setPosition(Position.ABSOLUTE);
		setPosition(imEle, startx, starty);
		Direction dir = Direction.getRandom();
		Timer timer = moveDirection(imEle, dir, 100);
		moveImage(imEle, endx, endy, timer);
		timer.scheduleRepeating(30);
	}

	private Timer moveDirection(Image imEle, Direction dir, int i) {
		int startx = imEle.getElement().getAbsoluteLeft();
		int starty = imEle.getElement().getAbsoluteTop();

		int endx = startx + dir.getX() * 200;
		int endy = starty + dir.getY() * 200;
		return moveImage(imEle, endx, endy, null);
	}

	public Timer moveImage(final Image imEle, final int endx, final int endy,
			final Timer next) {

		return new Timer() {
			int startx = imEle.getElement().getAbsoluteLeft();
			int starty = imEle.getElement().getAbsoluteTop();

			@Override
			public void run() {
				setPosition(imEle, startx, starty);
				boolean startEqualsEnd = startx != endx;
				if (startEqualsEnd) {
					startx++;
				}
				if (starty != endy) {
					starty++;

					if (startEqualsEnd) {
						this.cancel();
						if (next != null) {
							next.scheduleRepeating(30);
						}
					}
				}
			}
		};
	}

	public void setPosition(Image imEle, int startx, int starty) {
		imEle.getElement().getStyle().setLeft(startx, Unit.PX);
		imEle.getElement().getStyle().setTop(starty, Unit.PX);
	}
	@Override
	public PBase clone() {

		return new DrawFocus().copyProperties(this);
	}

}
