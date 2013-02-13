package gwt.client.game;

import java.util.Map;

import gwt.client.main.VConstants;
import gwt.shared.datamodel.VParams;

public class ExecuteOnTrue extends VParams{
	public ExecuteOnTrue() {
		// TODO Auto-generated constructor stub
	}
	String name;
	public ExecuteOnTrue(String name) {
		super();
		this.name = name;
	}
	@Override
	public void execute(Map<String, Object> map) {
		Boolean exp = (Boolean) map.get(VExpression.vexpression);
		if(exp != null&&exp){
			((VParams) get(name)).execute(map);
		}
	}
	

}
