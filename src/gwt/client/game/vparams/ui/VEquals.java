package gwt.client.game.vparams.ui;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.main.LocalTemplate;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class VEquals extends VParams {

	public VEquals() {
	
	}
	public VEquals(String type,String vp, Object toEqual) {
		super();
		put(VConstants.type,type);
		put(AttachUtil.OBJECT,vp);
		put(VConstants.toequal,toEqual);
	}
	public String getVp(){
		return getS(AttachUtil.OBJECT);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		
		if(get(VConstants.toequal).equals(map.get(AttachUtil.OBJECT))){
			Object vp = VExpression.getValue(getVp(), EntryPoint.game.getPBase(getS(VConstants.type)));
			((VParams) vp).execute(map);
		}
	}
	
	public PBase clone() {
		return new VEquals().copyProperties(this);
	}	
}
