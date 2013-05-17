package gwt.client.game.display;

import gwt.client.game.AttachUtil;
import gwt.client.map.HashMapData;

import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HMDDisplay extends UIVParams {
	VerticalPanel dhmd;

	public HMDDisplay() {
	
	}

	@Override
	public void execute(Map<String, Object> map) {
		HashMapData mapData = (HashMapData) map.get(AttachUtil.OBJECT);
		if(mapData == null){
			return;
		}

		HTML ht = (HTML) dhmd.getWidget(0);
		
		ht.setHTML(mapData.getPosition()+" <br> "+mapData.toString().replace("\n","<br>"));
		
		
		

		// ((Label)).setText(text+mapData.getPosition()
		// +"\n"+mapData.toString());
//		String url = "/images/fullsize/" + mapData.getValue() + ".jpg";
//		Image image = (Image) dhmd.getWidget(0);
//		if (image.getUrl().endsWith(url)) {
//			return;
//		}
//		image.setUrl(url);
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
		HTML w = new HTML();
		
		dhmd.add(w);

		w.setSize("20em", "35em");
		dhmd.setSize("20em", "20em");

	}
}
