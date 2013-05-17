package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.map.HashMapData;

import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LogDisplay extends UIVParams {
	public static final int debug = 1;
	public static final int run = 2;
	ListBox lb;

	public LogDisplay() {

	}

	public static void log(Object message, int level){
		LogDisplay ld=(LogDisplay) EntryPoint.game.get(VConstants.log);

		//dont show debug
		if(level < 2){
			return;
		}
		if(ld != null){
			ld.addMessage(""+message);	
		}
		
	}

	private void addMessage(String message) {
		lb.addItem(message);
		if(lb.getItemCount() > 10){
			lb.removeItem(0);
		}
	}

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return lb;
	}

	boolean showLog;
	@Override
	public void init() {
		lb = new ListBox(true);
		lb.setSize("30em", "40em");
		lb.setVisible(showLog);
	}
}
