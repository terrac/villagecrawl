package gwt.client.game.vparams.requirements;

import java.util.List;
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
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.game.vparams.adding.AddScore;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

public class StoryEvent extends VParams {
	
	
	public StoryEvent() {
		// TODO Auto-generated constructor stub
	}
	public StoryEvent(String ...list){
		put(VConstants.list,list);
	}
	
	public StoryEvent(String message, VParams ... actions){
		put(VConstants.list,new String[]{message});
		put(VConstants.vparams,actions);
	}
	
	
	public StoryEvent(String message){
		put(VConstants.list,new String[]{message});
	}
	
	int count = 0;
	DisplayAndOk dao;
	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing lb = getLivingBeing(map);
		if(map.get(VConstants.log) != null){
			
			List<String> l = getList(VConstants.list);
			for(String a :l){
				LogDisplay.log(lb.getName()+" "+a, 2);
			}
			return;
		}
		final Label label = new Label((String) getList(VConstants.list).get(0));
		count++;
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(count+1 >= getList(VConstants.list).size()){
					dao.end();
					for(Object o :getList(VConstants.vparams)){
						((VParams) o).execute(null);
					}
					return;
				}
				label.setText((String) getList(VConstants.list).get(count));
				count++;
				
			}
		});
		label.getElement().getStyle().setFontSize(4, Unit.EM);
		dao = new DisplayAndOk(new UVerticalPanel(label));
		dao.execute(null);
	}
	@Override	
	public PBase clone() {
		
		return new StoryEvent().copyProperties(this);
	}
	
}
