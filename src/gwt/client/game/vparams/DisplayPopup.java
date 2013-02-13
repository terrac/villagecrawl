package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.Pop;
import gwt.client.game.VExpression;
import gwt.client.game.display.MapDataBuyDisplay;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.output.HtmlOut;
import gwt.client.output.html.GCanvas;
import gwt.shared.datamodel.VParams;

public class DisplayPopup extends VParams implements Pop{

	public DisplayPopup() {

	}

	public DisplayPopup(UIVParams[] list, VParams... vparams) {

		put(VConstants.list, list);
		put(VConstants.vparams, vparams);
	}
	
	public DisplayPopup(List list) {

		put(VConstants.list, list);
		
	}

	public DisplayPopup(UIVParams list, VParams... vparams) {

		put(VConstants.list, new UIVParams[]{list});
		put(VConstants.vparams, vparams);
	}
	@Override
	public void execute(final Map<String, Object> map) {
		
		//HashMapData hmd = (HashMapData) map.get(AttachUtil.OBJECT);
		Point p;
		displaypopup(null, map,5);

	}
	PopupPanel db = new PopupPanel();
	public void displaypopup(LivingBeing p, final Map<String, Object> map,int turns) {
		if(!EntryPoint.game.getHtmlOut().currentFMD.equals( p.getParent().getParent())){
			return;
		}
		HorizontalPanel vp = new HorizontalPanel();
		for (UIVParams uv : (List<UIVParams>) getList(VConstants.list)) {
			Widget widgetAndInit = uv.getWidgetAndInit();
			if(widgetAndInit == null){
				continue;
			}
			vp.add(widgetAndInit);
			uv.execute(map);
		}

		
		
		db.setWidget(vp);
		GCanvas gcanvas=(GCanvas) EntryPoint.game.getHtmlOut().fmdMap.values().toArray()[0];
		
		db.setPopupPosition(gcanvas.getAbsoluteLeft()+p.getX()*HtmlOut.imagesize, gcanvas.getAbsoluteTop()+p.getY()*HtmlOut.imagesize);
		db.show();
		EntryPoint.game.getRunTurn().add(p,this,turns);
	}

	

	
	@Override
	public PBase clone() {

		return new DisplayPopup().copyProperties(this);
	}

	@Override
	public void pop() {
		db.hide();
		
	}
}
