package gwt.client.game.vparams.ui;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

import java.util.Map;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Score extends UIVParams{

	
	public Score() {	
	}
	
	public Score(String type, String description) {
		super();
		put(VConstants.type, type);
		put(VConstants.description,description);
		put(VConstants.toget, AttachUtil.attacher);
	}
	
	public Score(String type, String description,String toGet) {
		super();
		put(VConstants.type, type);
		put(VConstants.description,description);
		put(VConstants.toget, toGet);
		
	}

	Label label ;
	
	
	@Override
	public void execute(Map<String, Object> map) {
		String toget = getS(VConstants.toget);
		if(toget.contains(".")){
			Integer value=(Integer) VExpression.getValue(map, toget);
			label.setText(getS(VConstants.description)+": "+value );
			return;
		}
		
		
		
		PBase pb = (PBase) map.get(toget);
		execute(pb);
	}

	

	public void execute(PBase pb) {
		
		Integer a = pb.getInt(getS(VConstants.type));
		
		label.setText(getS(VConstants.description)+": "+a );
	}
	@Override
	public Widget getWidget() {

		return label;
	}
	
	@Override
	public UIVParams clone() {
		
		return new Score().copyProperties(this);
	}
	
	@Override
	public void init() {
		label  = new Label(getS(VConstants.description)+": "+getS(VConstants.type));
	}
}
