package gwt.client.game;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DeckPanel;

public class AdvDeckPanel extends DeckPanel{
	public AdvDeckPanel() {
		  super();
		  sinkEvents(Event.ONMOUSEUP | Event.ONDBLCLICK | Event.ONCONTEXTMENU);
		}
		 
		public void onBrowserEvent(Event event) {
		  //GWT.log("onBrowserEvent", null);
		  
		  switch (DOM.eventGetType(event)) {
		    case Event.ONMOUSEUP:
		      if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
		        //GWT.log("Event.BUTTON_LEFT", null);
		        //listener.onClick(this, event);
		      }
		 
		      if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {
		        //GWT.log("Event.BUTTON_RIGHT", null);
		    	  event.cancelBubble(true);//This will stop the event from being propagated
				  event.preventDefault();
		      }
		      break;
		    case Event.ONDBLCLICK:
		      break;
		 
		    case Event.ONCONTEXTMENU:
		      //GWT.log("Event.ONCONTEXTMENU", null);
		      break;
		 
		    default:
		      break; // Do nothing
		  }//end switch
		}
}
