package gwt.client.main.base;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.Window;

import gwt.client.edit.EditList;

public class Parameters implements Serializable{
	
	Object[] params;

	public Parameters(Object... params) {
		super();
		this.params = params;
	}

	public void setupActionParams(EditList el2) {
		for(int a = 0; a < params.length; a+=2){
			
				Object o = params[a+1];
				if(o instanceof List){
					String string = o.toString();
					o = string.substring(1,string.length()-1);
				}
				el2.addListItem((String) params[a],o.toString());
			
		}
		
	}
}
