package gwt.client.game.vparams.requirements;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import gwt.client.EntryPoint;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.game.vparams.adding.AddScore;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

public class TwoOptionEvent extends VParams {
	
	public TwoOptionEvent(String question, VParams firstChoice, VParams secondChoice) {
		this(question, null, null, firstChoice, secondChoice);
	}
	public TwoOptionEvent() {
		// TODO Auto-generated constructor stub
	}
	public TwoOptionEvent(String question, String response1,String response2,VParams firstChoice, VParams secondChoice) {
		put(VConstants.question,question);
		put(VConstants.agree,new PBase(VConstants.response,response1,VConstants.vparams,firstChoice));
		put(VConstants.disagree,new PBase(VConstants.response,response2,VConstants.vparams,secondChoice));
		
	}
	DisplayAndOk dao;
	@Override
	public void execute(Map<String, Object> map) {
		
		Label label = new Label(getS(VConstants.question));
		HorizontalPanel hp = new HorizontalPanel();
		Button button = new Button("Agree", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String response = getPBase(VConstants.agree).getS(VConstants.response);
				if(response != null&&!response.isEmpty()){
					Window.alert(response);					
				}
				VParams vp=(VParams) getPBase(VConstants.agree).get(VConstants.vparams);
				vp.execute(null);
				dao.end();
			}
		});
		hp.add(button);

		Button decline = new Button("Disagree", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String response = getPBase(VConstants.disagree).getS(VConstants.response);
				if(!response.isEmpty()){
					Window.alert(response);					
				}
				VParams vp=(VParams) getPBase(VConstants.disagree).get(VConstants.vparams);
				vp.execute(null);
				dao.end();

			}
		});
		hp.add(decline);

		label.getElement().getStyle().setFontSize(4, Unit.EM);
		button.getElement().getStyle().setFontSize(4, Unit.EM);
		decline.getElement().getStyle().setFontSize(4, Unit.EM);
//		Image image = new Image(item.getImage());
//		image.setSize("64px", "64px");
//		hp.add(image);
		dao = new DisplayAndOk(new UVerticalPanel(label, hp));
		dao.execute(null);
	}
	@Override	
	public PBase clone() {
		
		return new TwoOptionEvent().copyProperties(this);
	}
	
}
