package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.ui.PersonDisplay;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DisplayChoices extends VParams {

	public DisplayChoices(String description,Object ... choices) {
		put(VConstants.description,description);
		put(VConstants.list,Arrays.asList(choices));
	}


	public DisplayChoices() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void execute(final Map<String, Object> map) {
		PBase object = (PBase) map.get(AttachUtil.OBJECT);
		
		String description = getS(VConstants.description);
		String[] descA = description.split("@");
		for(int a = 0; a < descA.length; a++){
			String macroed=(String) VExpression.getValue(descA[a], object);
			if(macroed != null){
				descA[a]= macroed;
			}
		}
		
		
		//get person off of the map
		
		//do some text with a macro to display the person/person values
		
		//then add all of the associated choices at the bottom
		Label label = new Label(description);
		label.getElement().getStyle().setFontSize(3, Unit.EM);
		PersonDisplay personDisplay = new PersonDisplay(getLivingBeing(map));
		UVerticalPanel uVerticalPanel = new UVerticalPanel(label);
		DisplayAndOk dao=new DisplayAndOk(new UIVParams[]{uVerticalPanel,personDisplay});
		dao.put(VConstants.closeonclick, true);
		dao.execute(map);
	}

	@Override
	public PBase clone() {

		return new DisplayChoices().copyProperties(this);
	}

}
