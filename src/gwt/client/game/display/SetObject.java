package gwt.client.game.display;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class SetObject extends VParams {
	public SetObject() {
		// TODO Auto-generated constructor stub
	}
	String type;
	public SetObject(String type, PBase pb) {
		super();
		this.type = type;
		this.pb = pb;
	}
	PBase pb;
	@Override
	public void execute(Map<String, Object> map) {
		pb.put(type, map.get(AttachUtil.OBJECT));
	}
}
