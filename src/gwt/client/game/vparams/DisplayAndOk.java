package gwt.client.game.vparams;

import gwt.client.EntryPoint;
import gwt.client.game.display.UIVParams;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DisplayAndOk extends VParams {

	public DisplayAndOk() {

	}

	public DisplayAndOk(UIVParams[] list, VParams... vparams) {

		put(VConstants.list, Arrays.asList(list));
		put(VConstants.vparams, vparams);
	}
	

	

	public DisplayAndOk(UIVParams list, VParams... vparams) {
		put(VConstants.list, Arrays.asList(new UIVParams[]{list}));
		put(VConstants.vparams, vparams);
	}

	PopupPanel db;
	static boolean showing = false;
	@Override
	public void execute(final Map<String, Object> map) {
		if(showing){
			return;
		}
		//HashMapData hmd = (HashMapData) map.get(AttachUtil.OBJECT);

		VerticalPanel vp = new VerticalPanel();
		
		
		String displaytext = "";
		for (UIVParams uv : (List<UIVParams>) getList(VConstants.list)) {
			vp.add(uv.getWidgetAndInit());
			uv.execute(map);
		}
		// displaytext = displaytext.replace("\n", "<br>");
		// save, and if they come back then reproduce last generalization


		db = new PopupPanel();
		//addOKCancel(map, vp);
		if(getB(VConstants.closeonclick)){
			FocusPanel fp = new FocusPanel(vp);
			fp.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					end();
				}
			});
			db.setWidget(fp);
			
		} else {
			db.setWidget(vp);			
		}
		db.show();
		showing = true;
		EntryPoint.game.getHtmlOut().refreshFmds();
		EntryPoint.game.stop();
		// select panel
		
	}

	private void addOKCancel(final Map<String, Object> map, VerticalPanel vp) {
		HorizontalPanel hp = new HorizontalPanel();
		Button ok = new Button("ok");
		ok.setSize("5em", "5em");
		
			
		
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				db.hide();
				showing = false;
				if(hasVParams()){
					for (VParams vp : (List<VParams>) getList(VConstants.vparams)) {
						vp.execute(map);
					}
				}
				EntryPoint.game.pauseToggle();

			}
		});
		hp.add(ok);
		if(hasVParams()){
			Button cancel = new Button("cancel");
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					end();
				}
			});
			hp.add(cancel);
		}

		vp.add(hp);
	}

	private boolean hasVParams() {
		List object = getList(VConstants.vparams);
		return object != null&&object.size() != 0;
	}

	HandlerRegistration hru;

	@Override
	public PBase clone() {

		return new DisplayAndOk().copyProperties(this);
	}

	public void end() {
		db.hide();
		showing = false;
		EntryPoint.game.go();
	}
}
