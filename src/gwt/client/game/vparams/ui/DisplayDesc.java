package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.game.util.PUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DisplayDesc extends UIVParams {
	public DisplayDesc() {
	}

public DisplayDesc(String... strings) {
		
		put(VConstants.list, strings);
	}
	public DisplayDesc(List strings) {
		
		put(VConstants.list, strings);
	}

	public void execute(java.util.Map<String, Object> map) {
		 
		for (String s : (List<String>) getList(VConstants.list)) {
			Object val= VExpression.getValue(map, s);
			if(val == null){
				continue;
			}
			String displaytext = "";
			if(val instanceof PBase){
				for(Entry<String, Object> e : ((PBase) val).getObjMap().entrySet()){
					if(e.getValue() instanceof String){
						PBase pb=PUtil.getFromChildren(EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.templatemap),(String) e.getValue());
						if(pb != null&&pb.containsKey(VConstants.description)){
							displaytext +=e.getKey()+" "+e.getValue() +"\n"+ pb.getS(VConstants.description)+"\n";
						}
					}
					
				}
				
			}
			if(displaytext.equals("")){
				displaytext += val;
			}
			if(s.endsWith("image")){
				vp.add(new Image(displaytext));
			} else {
				vp.add(new HTML(displaytext.replace("\n", "<br>")));
						
			}
		}
	}

	VerticalPanel vp;

	@Override
	public Widget getWidget() {

		return vp;
	}

	@Override
	public UIVParams clone() {

		return new DisplayDesc().copyProperties(this);
	}

	@Override
	public void init() {
		vp = new VerticalPanel();
	}
}
