package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.PlaySound;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.output.OutputDirector;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TemplateDisableDisplay extends UIVParams {
	VerticalPanel vp;

	public TemplateDisableDisplay() {
	}

	@Override
	public void execute(Map<String, Object> map) {
//		EntryPoint.game.getHtmlOut().flextable.setWidget(1, 2,
//				getWidgetAndInit());
//		HashMapData hmd = (HashMapData) map.get(AttachUtil.OBJECT);
//		final LivingBeing lb = hmd.getLivingBeing();
//
//		vp.clear();
//
//		for (final Entry<String, Object> ent : lb.getTemplate()
//				.getRationalMap().getObjMap().entrySet()) {
//			HorizontalPanel hp = new HorizontalPanel();
//			vp.add(hp);
//			hp.add(new Label(ent.getKey() + ": " + ent.getValue()));
//			final Button button = new Button("disable");
//			button.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					if (lb.getTemplate().disableSet.contains(ent.getKey())) {
//						lb.getTemplate().disableSet.remove(ent.getKey());
//						button.setText("enable");
//					} else {
//						lb.getTemplate().disableSet.add(ent.getKey());
//						button.setText("disable");
//					}
//
//				}
//			});
//			hp.add(button);
//		}
	}

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return vp;
	}

	@Override
	public UIVParams clone() {
		return new TemplateDisableDisplay().copyProperties(this);
	}

	@Override
	public void init() {
		vp = new VerticalPanel();
	}
}
