package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.CategoryMap;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.output.HtmlOut;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatWindow extends UIVParams {
	public ChatWindow() {
	}

	

	public void execute(java.util.Map<String, Object> map) {
		//get category maps and appropriate categories

		
		// get category map on fmd
		// run through attributes and call category map
		//String personality =getLivingBeing(map).getPBase(VConstants.attributes).getS(VConstants.personality);
		CategoryMap cm=(CategoryMap) EntryPoint.game.getHtmlOut().currentFMD.getPBase(VConstants.script);
		//(calling it uses it up)
		if(cm == null){
			return;
		}
		//String message=cm.getS(personality);
		

		for(List<String> l :(List<List<String>>) cm.getList(VConstants.list)){
			//if a successfull call occurs then display image and category map statement
			HorizontalPanel hp = new HorizontalPanel();
			Image img = new Image("/images/"+l.get(0)+".png");
			img.setPixelSize(HtmlOut.getAvatarSize(), HtmlOut.getAvatarSize());
			hp.add(img);
			hp.add(new Label(l.get(1)));
			vp.add(hp);
		}
		
		
	}

	VerticalPanel vp;

	@Override
	public Widget getWidget() {

		return vp;
	}

	@Override
	public UIVParams clone() {

		return new ChatWindow().copyProperties(this);
	}

	@Override
	public void init() {
		
		vp = new VerticalPanel();
	}
}
