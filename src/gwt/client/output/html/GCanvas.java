/**
 * 
 */
package gwt.client.output.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.Attack;
import gwt.client.game.GameUtil;
import gwt.client.game.display.PauseDisplay;
import gwt.client.main.Move;
import gwt.client.main.PickUp;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.output.HtmlOut;
import gwt.client.output.ImageCache;
import gwt.shared.datamodel.VParams;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class GCanvas extends FocusWidget {
	/**
	 * 
	 */
	private HtmlOut htmlOut;

	Point point;
	boolean shouldUnpauseAfter;
	long time = System.currentTimeMillis();
	public GCanvas(HtmlOut hOut, int coordX, int coordY) {
		
		this(coordX,coordY);
		sinkEvents(Event.ONMOUSEUP | Event.ONDBLCLICK | Event.ONCONTEXTMENU
				| Event.ONMOUSEMOVE | Event.ONKEYDOWN | Event.ONKEYPRESS|Event.TOUCHEVENTS);
		getContext2d().setLineWidth(1);

		this.htmlOut = hOut;

		Event.addNativePreviewHandler(new NativePreviewHandler() {

			@Override
			public void onPreviewNativeEvent(NativePreviewEvent et) {
//				if(System.currentTimeMillis() - time< 300){
//					et.cancel();
//					return;
//				}
				
				time = System.currentTimeMillis();
				// TODO Auto-generated method stub
				NativeEvent event=et.getNativeEvent();
				ImageCache imgCache=htmlOut.currentFMD.getImgCache();
				boolean flag = false;
				if (event.getKeyCode() == KeyCodes.KEY_LEFT) {
					et.cancel();
					if (htmlOut.currentFMD.xdisplay <= 0) {
						
						return;
					}
					htmlOut.currentFMD.xdisplay--;
				
					getContext2d().putImageData(getContext2d().getImageData(0,0,getCoordinateSpaceWidth(),getCoordinateSpaceHeight()),0,0);
					imgCache.clearHorizontalLine(imgCache.size-1);
					flag = true;
				}
				if (event.getKeyCode() == KeyCodes.KEY_RIGHT) {
					et.cancel();
					System.out.println("right"+htmlOut.currentFMD.xdisplay);
					if (htmlOut.currentFMD.xdisplay+ imgCache.size >= htmlOut.currentFMD.getXsize()) {
						return;
					}
					htmlOut.currentFMD.xdisplay++;
					
					getContext2d().putImageData(getContext2d().getImageData(0,0,getCoordinateSpaceWidth(),getCoordinateSpaceHeight()),0,0);
					imgCache.clearHorizontalLine(0);
					flag = true;
				}
				if (event.getKeyCode() == KeyCodes.KEY_DOWN) {
					et.cancel();
					if (htmlOut.currentFMD.ydisplay + imgCache.size >= htmlOut.currentFMD.getYsize()) {						
						return;
					}
					htmlOut.currentFMD.ydisplay++;
					
					getContext2d().putImageData(getContext2d().getImageData(0,0,getCoordinateSpaceWidth(),getCoordinateSpaceHeight()),0,0);
					imgCache.clearVerticalLine(0);
					flag = true;
				}
				if (event.getKeyCode() == KeyCodes.KEY_UP) {
					et.cancel();
					if (htmlOut.currentFMD.ydisplay <= 0) {						
						return;
					}
					htmlOut.currentFMD.ydisplay--;
					
					getContext2d().putImageData(getContext2d().getImageData(0,0,getCoordinateSpaceWidth(),getCoordinateSpaceHeight()),0,0);
					imgCache.clearVerticalLine(imgCache.size-1);
					flag = true;
				}
				if(!flag){
					return;
				}
				
				
				
				
				
				
				
				System.out.println(htmlOut.currentFMD.xdisplay);
				System.out.println(htmlOut.currentFMD.ydisplay);
				htmlOut.drawAreaMap(htmlOut.currentFMD, htmlOut.fmdMap.get(htmlOut.currentFMD));
			}
		});

	}

	public GCanvas(int coordX, int coordY) {
		setElement(Document.get().createCanvasElement());
		// super(coordX, coordY);
		setCoordinateSpaceHeight(coordY);
		setCoordinateSpaceWidth(coordX);
		
	}

	
	public void clear() {
		getContext2d().clearRect(0, 0, getCoordinateSpaceWidth(),
				getCoordinateSpaceHeight());
		
	}
	
	

	public void onBrowserEvent(Event event) {
		// GWT.log("onBrowserEvent", null);
		
		event.stopPropagation();
		event.preventDefault();
		if (DOM.eventGetButton(event) != Event.BUTTON_RIGHT) {
			super.onBrowserEvent(event);
		}

		int x, y;
		switch (DOM.eventGetType(event)) {
		
		case Event.ONMOUSEDOWN:
			if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
				x = getCanvasX(event);

				y = getCanvasY(event);

				point = new Point(x, y);
				// shouldUnpauseAfter = !EntryPoint.game.isPaused();
			}

			break;

		case Event.ONMOUSEMOVE:
			if (point == null) {
				break;
			}
			// EntryPoint.game.pause();
			x = getCanvasX(event);

			y = getCanvasY(event);
			getContext2d().setStrokeStyle(HtmlOut.green);
			// int i=point.x/ HtmlOut.imagesize ;
			// int j=point.y/HtmlOut.imagesize ;
			//			
			// int k = () /HtmlOut.imagesize ;
			// int l = (-point.y + y) /HtmlOut.imagesize ;

			Point upperLeft = point.clone();
			Point lowerRight = new Point(x, y);
			setUpperLeftAndLowerRight(upperLeft, lowerRight);
			
			htmlOut.drawAreaMap(htmlOut.currentFMD, htmlOut.fmdMap
					.get(htmlOut.currentFMD), htmlOut.imagesize,htmlOut.currentFMD.xdisplay,htmlOut.currentFMD.ydisplay, lowerRight.x , lowerRight.y , upperLeft.x
					, upperLeft.y,
					false);
			getContext2d().strokeRect(upperLeft.x, upperLeft.y,
					-upperLeft.x + lowerRight.x, -upperLeft.y + lowerRight.y);

			break;
		case Event.ONMOUSEUP:
			if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
				// if(shouldUnpauseAfter&&EntryPoint.game.isPaused()){
				// EntryPoint.game.pauseToggle();
				// }

				if (point != null) {
					if (EntryPoint.mobile) {
						doRightClick(event);
					} else {
						doLeftClick(event);
					}

					htmlOut.refreshFmds();
					point = null;
				}

			}

			if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {
				// GWT.log("Event.BUTTON_RIGHT", null);
				doRightClick(event);

			}
			break;
		case Event.ONDBLCLICK:
			break;

		case Event.ONCONTEXTMENU:
			// GWT.log("Event.ONCONTEXTMENU", null);
			break;

		default:
			break; // Do nothing
		}// end switch
	}

	private void doLeftClick(Event event) {
		int x;
		int y;
		x = getCanvasX(event);

		y = getCanvasY(event);
		
		// take the current pos, take the point. Every point inside
		// the rectangle
		// is checked and if a lb is there then it is added to the
		// list
		htmlOut.cfList.clear();
		Point upperLeft = point;
		Point lowerRight = new Point(x, y);
		setUpperLeftAndLowerRight(upperLeft, lowerRight);

		upperLeft.x += htmlOut.currentFMD.xdisplay;
		upperLeft.y += htmlOut.currentFMD.ydisplay;
		lowerRight.x += htmlOut.currentFMD.xdisplay;
		lowerRight.y += htmlOut.currentFMD.ydisplay;
		
		if (upperLeft.x == lowerRight.x) {
			lowerRight.x += 1;
		}
		if (upperLeft.y == lowerRight.y) {
			lowerRight.y += 1;
		}

		for (Entry<FullMapData, GCanvas> ent : this.htmlOut.fmdMap.entrySet()) {
			if (ent.getValue().equals(this)) {
				for (HashMapData hmd : ent.getKey()) {					
					
					
					Point hmdpoint = hmd.getPosition();


					if (!Point.outsideRect(upperLeft, lowerRight, hmdpoint)) {
						ent.getKey().getImgCache().clearPositional(hmdpoint.x,hmdpoint.y);
						if (hmd.getLivingBeing() != null) {
							htmlOut.cfList.add(hmd.getLivingBeing());
						}
						
						if (hmd.getParent().containsKey(VConstants.leftclick)) {
							AttachUtil.run(VConstants.leftclick, hmd, hmd
									.getParent());
							// break;
						}
						htmlOut.selectedTile = hmd;
					}
				}
			}
		}
//		htmlOut.cfList = GameUtil.getPlayerTeam(htmlOut.cfList);
//		if (htmlOut.cfList.size() > 0
//				&& !htmlOut.cfList.contains(htmlOut.getCurrentlyFollowed())) {
//			htmlOut.setCurrentlyFollowed(htmlOut.cfList.get(0));
//		} else {
//			htmlOut.setCurrentlyFollowed(null);
//		}
	}

	public void setUpperLeftAndLowerRight(Point upperLeft, Point lowerRight) {
		if (upperLeft.x > lowerRight.x) {
			int xu = upperLeft.x;
			upperLeft.x = lowerRight.x;
			lowerRight.x = xu;
		}
		if (upperLeft.y > lowerRight.y) {
			int yu = upperLeft.y;
			upperLeft.y = lowerRight.y;
			lowerRight.y = yu;
		}

		upperLeft.x = upperLeft.x / HtmlOut.imagesize;
		upperLeft.y = upperLeft.y / HtmlOut.imagesize;
		lowerRight.x = lowerRight.x / HtmlOut.imagesize;
		lowerRight.y = lowerRight.y / HtmlOut.imagesize;
		
		
		
	}

	private void doRightClick(Event event) {
//		int x;
//		int y;
//		x = getCanvasX(event);
//
//		y = getCanvasY(event);
//
//		LivingBeing currentlyFollowed = this.htmlOut.getCurrentlyFollowed();
//		if (currentlyFollowed == null || currentlyFollowed.getParent() == null) {
//			return;
//		}
//		HashMapData hmd = currentlyFollowed.getParent().getParent().getData(
//				x / HtmlOut.imagesize, y / HtmlOut.imagesize);
//
//		if (htmlOut.cfList.size() < 1) {
//			htmlOut.cfList.add(currentlyFollowed);
//		}
//
//		if (hmd.getParent().containsKey(VConstants.rightclick)) {
//			AttachUtil.run(VConstants.rightclick, hmd, hmd.getParent());
//			return;
//		}
//		for (LivingBeing cf : htmlOut.cfList) {
//			cf.getTemplate().clear();
//			// should fix to put in a proper action , and then attach
//			// via a param (or maybe just an oobject?)
//
//			LivingBeing lb = hmd.getLivingBeing();
//
//			if (lb != null && lb.get(VConstants.team) != null
//					&& !lb.get(VConstants.team).equals(cf.get(VConstants.team))) {
//				cf.getTemplate().push(new Attack(lb, cf));
//				continue;
//			}
//			cf.getTemplate().push(new PickUp());
//			cf.getTemplate().push(new Move(hmd, "movetoandgather"));
//		}
	}

	protected int getCanvasX(Event event) {
		int i = event.getClientX() - getAbsoluteLeft()
				+ getElement().getScrollLeft()
				+ getElement().getOwnerDocument().getScrollLeft();
		
		return i;
	}

	private int getCanvasY(Event event) {
		int i = event.getClientY() - getAbsoluteTop()
				+ getElement().getScrollTop()
				+ getElement().getOwnerDocument().getScrollTop();
		
		return i;

	}

	/**
	 * Returns a 2D rendering context.
	 * 
	 * This is a convenience method, see {@link #getContext(String)}.
	 * 
	 * @return a 2D canvas rendering context
	 */
	public Context2d getContext2d() {
		return getCanvasElement().getContext2d();
	}

	/**
	 * Gets the height of the internal canvas coordinate space.
	 * 
	 * @return the height, in pixels
	 * @see #setCoordinateSpaceHeight(int)
	 */
	public int getCoordinateSpaceHeight() {
		return getCanvasElement().getHeight();
	}

	/**
	 * Gets the width of the internal canvas coordinate space.
	 * 
	 * @return the width, in pixels
	 * @see #setCoordinateSpaceWidth(int)
	 */
	public int getCoordinateSpaceWidth() {
		return getCanvasElement().getWidth();
	}

	/**
	 * Sets the height of the internal canvas coordinate space.
	 * 
	 * @param height
	 *            the height, in pixels
	 * @see #getCoordinateSpaceHeight()
	 */
	public void setCoordinateSpaceHeight(int height) {
		getCanvasElement().setHeight(height);
	}

	/**
	 * Sets the width of the internal canvas coordinate space.
	 * 
	 * @param width
	 *            the width, in pixels
	 * @see #getCoordinateSpaceWidth()
	 */
	public void setCoordinateSpaceWidth(int width) {
		getCanvasElement().setWidth(width);
	}

	public CanvasElement getCanvasElement() {
		return this.getElement().cast();
	}

}