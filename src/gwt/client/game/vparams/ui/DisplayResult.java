package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.UIVParams;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DisplayResult extends VParams {

	public DisplayResult() {

	}


	public DisplayResult( int displaySize, PBase...results) {
		
		put(VConstants.display,displaySize);
		put(VConstants.list,Arrays.asList(results));
	}
	
	public DisplayResult(PBase...results) {
		this( 2, results);
	}	

	@Override
	public void execute(final Map<String, Object> map) {
		PBase result =(PBase) VConstants.getRandomFromList(getList(VConstants.list));
		
		// person comes from personkeyvalue, persondisplay shows stats, image, and name
		Label label = new Label(result.getS(VConstants.message));
		
		label.getElement().getStyle().setFontSize(3, Unit.EM);
		PersonDisplay personDisplay = new PersonDisplay(getLivingBeing(map));
		UVerticalPanel uVerticalPanel = new UVerticalPanel(label);
		if(result.containsKey(VConstants.image)){
			Image image = new Image(MapData.getImageString(result.getS(VConstants.image)));
			uVerticalPanel.vp.add(image);
		}
		DisplayAndOk dao=new DisplayAndOk(new UIVParams[]{uVerticalPanel,personDisplay});
		dao.put(VConstants.closeonclick, true);
		dao.execute(map);
	}
	

	@Override
	public PBase clone() {

		return new DisplayResult().copyProperties(this);
	}

}
