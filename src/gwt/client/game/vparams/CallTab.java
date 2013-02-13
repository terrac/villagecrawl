package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.display.MapDataBuyDisplay;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class CallTab extends VParams {

	public CallTab() {
		
	}
	public CallTab(Object rc, String display) {
		put(VConstants.random,rc);
		put(VConstants.display,display);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		
		if(!GameUtil.getPlayerTeam().equals(lb.getTeam())){
			return;
		}
		//make sure to call sell all
		
		//generate randomization based on the map and the person
		RandomCreation randomCreation = (RandomCreation) AttachUtil.getMappedParam(get(VConstants.random));
		int size = getInt(VConstants.size);
		if( size == 0){
			size = 10;
		}
		List randomlist = new ArrayList();
		for(int a = 0; a < size;a++){
			randomlist.add(randomCreation.randomize(null));
		}
		
		//save, and if they come back then reproduce last generalization
		
		final TabPanel tabpanel = EntryPoint.game.getHtmlOut().panel;
		VerticalPanel vp = new VerticalPanel();
		UIVParams uiv = ((UIVParams) AttachUtil.getMappedParam(get(VConstants.display))).clone();
		tabpanel.add(vp,"current");
		vp.add(uiv.getWidgetAndInit());
		uiv.setList(randomlist);
		
		
		//select panel
		tabpanel.selectTab(tabpanel.getWidgetCount()-1);		
		
		//remove panel once the tab selection goes back to 0 and remove attach handler
		hru=tabpanel.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if(event.getSelectedItem() == 0){
					hru.removeHandler();
					EntryPoint.game.getHtmlOut().panel.remove(EntryPoint.game.getHtmlOut().panel.getWidgetCount()-1);
					
					
				}
				
			}
		});
		
		Button bt = new Button("Back to map");
		vp.add(bt);
		bt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				tabpanel.selectTab(0);
				
			}
		});
		
	}
	HandlerRegistration hru;
	@Override
	public PBase clone() {
		
		return new CallTab().copyProperties(this);
	}
}
