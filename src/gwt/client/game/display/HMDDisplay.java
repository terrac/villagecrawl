package gwt.client.game.display;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HMDDisplay extends UIVParams {
	VerticalPanel dhmd;
	HashMapData current;
	public HMDDisplay() {
	
	}

	@Override
	public void execute(Map<String, Object> map) {
		setup(map);
		if(current == null){
			return;
		}
		Demographics d = (Demographics) current.get(VConstants.demographics);
		
		HTML ht = (HTML) dhmd.getWidget(0);
		if(d != null){
			ht.setVisible(false);
			d.c.setVisible(true);
			d.execute();
		}else {
			ht.setVisible(true);
			d.c.setVisible(false);
		}
		ht.setHTML(current.getPosition()+" <br> "+current.toString().replace("\n","<br>"));
		

		// ((Label)).setText(text+mapData.getPosition()
		// +"\n"+mapData.toString());
//		String url = "/images/fullsize/" + mapData.getValue() + ".jpg";
//		Image image = (Image) dhmd.getWidget(0);
//		if (image.getUrl().endsWith(url)) {
//			return;
//		}
//		image.setUrl(url);
	}

	public void setup(Map<String, Object> map) {
		HashMapData mapData = (HashMapData) map.get(AttachUtil.OBJECT);
		if(mapData != null){
			current = mapData;
		}
		
	}

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return dhmd;
	}

	@Override
	public void init() {
		dhmd = new VerticalPanel();
//		Image image = new Image();
//		dhmd.add(image);
		final HTML w = new HTML();
		
		dhmd.add(w);
		dhmd.add(Demographics.c);

		w.setSize("20em", "21em");
		dhmd.setSize("20em", "20em");
		w.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert(w.getHTML());
			}
		});
	}
}
